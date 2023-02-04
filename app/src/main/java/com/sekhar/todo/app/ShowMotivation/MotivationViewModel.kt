package com.sekhar.todo.app.ShowMotivation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sekhar.todo.app.repo.network.Motivation
import com.sekhar.todo.app.repo.network.MotivationApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

enum class ApiStatus{LOADING, ERROR, DONE}

class MotivationViewModel : ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    private var _quote = MutableLiveData<Motivation>()
    val quote : LiveData<Motivation>
    get() = _quote

    private val _status = MutableLiveData<ApiStatus>()
    val status : LiveData<ApiStatus>
    get() = _status

    
    var error = MutableLiveData<String>()

    init{
        getMotivationQuotes()
    }

    fun getMotivationQuotes() {

        _status.value= ApiStatus.LOADING

        coroutineScope.launch {
            try {
            val data = MotivationApi.retrofitService.getMotivation()

                var index = rand(0, data.size)
                // some objects are empty:
                while(data[index].title.equals("") and
                    data[index].author.equals("")  )
                    index = rand(0, data.size)

                _quote.value = data[index]

                _status.value= ApiStatus.DONE
            }catch (e : Exception){
                Log.e("ERROR", "Error: "+e.message)
                error.value = "Error getting motivation 😥"
                _status.value= ApiStatus.ERROR
            }
        }

        /*****
         *
         * OLD WAY:
         */
//        MotivationApi.retrofitService.getMotivation().enqueue(object:retrofit2.Callback<List<Motivation>> {
//
//            // HTTP response
//            override fun onResponse(
//                call: Call<List<Motivation>>,
//                response: Response<List<Motivation>>
//            ) {
//
//                _quote.value = "Success: ${response.body()?.size} Mars properties retrieved"
//            }
//
//            /**
//             * Invoked when a network exception occurred talking to the server or when an unexpected exception
//             * occurred creating the request or processing the response.
//             */
//            override fun onFailure(
//                call: Call<List<Motivation>>,
//                t: Throwable
//            ) {
//                _quote.value = "Error bro ${t.message}"
//            }})
    }

    fun rand(from: Int, to: Int) : Int {
        return Random().nextInt(to - from) + from
    }
}