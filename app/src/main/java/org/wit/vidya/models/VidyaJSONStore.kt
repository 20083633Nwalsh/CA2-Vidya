package org.wit.vidya.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import org.wit.vidya.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

const val JSON_FILE = "games.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<VidyaModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class VidyaJSONStore(private val context: Context) : VidyaStore {

    var games = mutableListOf<VidyaModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<VidyaModel> {
        logAll()
        return games
    }

    override fun create(vidya: VidyaModel) {
        vidya.id = generateRandomId()
        games.add(vidya)
        serialize()
    }


    override fun update(vidya: VidyaModel) {
        var foundVidya: VidyaModel? = games.find { p -> p.id == vidya.id }
        if (foundVidya != null) {
            foundVidya.title = vidya.title
            foundVidya.description = vidya.description
            foundVidya.year = vidya.year
            foundVidya.image = vidya.image
            foundVidya.lat = vidya.lat
            foundVidya.lng = vidya.lng
            foundVidya.zoom = vidya.zoom
            logAll()
        }
    }

    override fun delete(vidya: VidyaModel) {
        games.remove(vidya)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(games, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        games = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll() {
       games.forEach { Timber.i("$it") }
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}