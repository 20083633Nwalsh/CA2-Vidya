package org.wit.musicka.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import org.wit.musicka.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

const val JSON_FILE = "games.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<MusickaModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class MusickaJSONStore(private val context: Context) : MusickaStore {

    var games = mutableListOf<MusickaModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<MusickaModel> {
        logAll()
        return games
    }

    override fun create(musicka: MusickaModel) {
        musicka.id = generateRandomId()
        games.add(musicka)
        serialize()
    }


    override fun update(musicka: MusickaModel) {
        var foundMusicka: MusickaModel? = games.find { p -> p.id == musicka.id }
        if (foundMusicka != null) {
            foundMusicka.title = musicka.title
            foundMusicka.description = musicka.description
            foundMusicka.year = musicka.year
            foundMusicka.image = musicka.image
            foundMusicka.lat = musicka.lat
            foundMusicka.lng = musicka.lng
            foundMusicka.zoom = musicka.zoom
            logAll()
        }
    }

    override fun delete(musicka: MusickaModel) {
        games.remove(musicka)
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