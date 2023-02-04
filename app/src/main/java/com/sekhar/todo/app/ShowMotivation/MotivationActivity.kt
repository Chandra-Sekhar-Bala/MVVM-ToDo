package com.sekhar.todo.app.ShowMotivation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.sekhar.todo.app.R
import com.sekhar.todo.app.databinding.ActivityMotivationBinding

class MotivationActivity : AppCompatActivity() {

    private  lateinit var bind : ActivityMotivationBinding
    private lateinit var viewModel: MotivationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMotivationBinding.inflate(layoutInflater)
        setContentView(bind.root)
    }

    override fun onResume() {
        super.onResume()

        viewModel = ViewModelProvider(this)[MotivationViewModel::class.java]

        // swipe refresh

        bind.swipeRefresh.setOnRefreshListener {
            viewModel.getMotivationQuotes()
        }

        // quote response message observer
        viewModel.quote.observe(this){ quote ->
            bind.quote.text = quote.title
            bind.author.text = String.format(getString(R.string.author),quote.author)
        }

        // error message observer
        viewModel.error.observe(this){ error->
            bind.quote.text = error
        }

        // status response observer
        viewModel.status.observe(this){ status->
            bind.progressBar.visibility =  when(status){
                ApiStatus.LOADING ->  View.VISIBLE
                ApiStatus.DONE ->  View.GONE
                ApiStatus.ERROR -> View.GONE
            }
            bind.swipeRefresh.isRefreshing = false
        }
    }

}