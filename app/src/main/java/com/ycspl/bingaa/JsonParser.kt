package com.ycspl.bingaa

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import java.nio.charset.Charset


object JsonParser {
    fun getListOfStates(context: Context): MutableList<StateItem> {
        lateinit var jsonString: String
        try {
            jsonString = context.assets.open("indian_states_and_districts.json")
                .bufferedReader()
                .use { it.readText() }
        } catch (ioException: IOException) {
            Log.d("TAG", "getCountryCode: ${ioException.localizedMessage}")
        }

        val products = JSONArray(jsonString)

        for (i in 0 until products.length()) {
            val objects: JSONObject = products.getJSONObject(i)
            objects.put("id", i+1)
            val cities = objects.getJSONArray("cities")
            val newCityArray = JSONArray()
            for (j in 0 until cities.length()) {
                 val cityObject = JSONObject()
                  cityObject.put("id", j+1)
                  cityObject.put("name", cities[j])
                  cityObject.put("state_id", objects.get("id"))
                  newCityArray.put(cityObject)
            }
            objects.put("cities", newCityArray)
        }


        Log.d("TAG", "getListOfStates: $products")

//
//        val file:String = fileName.text.toString()
//        val data:String = fileData.text.toString()
//        val fileOutputStream: FileOutputStream
//        try {
//            fileOutputStream = openFileOutput(file, Context.MODE_PRIVATE)
//            fileOutputStream.write(data.toByteArray())
//        }catch (e: Exception){
//            e.printStackTrace()
//        }

        val listCountryType = object : TypeToken<MutableList<StateItem>>() {}.type
        return Gson().fromJson(products.toString(), listCountryType)
    }

}