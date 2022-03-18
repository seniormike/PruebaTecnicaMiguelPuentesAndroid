package com.mapr.credibanco.tools

/**
 * Singleton reference object that manages constants in project.
 * @author {MAPR - Dec 2021}
 */
object Constants {

   const val BASE_URL = "http://ws.audioscrobbler.com/2.0/"

   // Default values
   const val API_KEY = "cf2894b9c73a323e24f5c6a9aab1eb85"
   const val METHOD_GET_ARTISTS = "geo.gettopartists"
   const val METHOD_GET_TRACKS = "artist.getTopTracks"
   const val FORMAT_JSON = "json"
   const val LIMIT_NUM_ARTISTS = 10

   // Authorization response status
   const val SUCCESS_STATUS = 200

   // Custom Dialog Tag
   const val CUSTOM_DIALOG_DETAIL = "CUSTOM_DIALOG_DETAIL"
}