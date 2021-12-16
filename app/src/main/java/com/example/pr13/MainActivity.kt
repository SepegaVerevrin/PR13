package com.example.pr13
// Осуществите поиск всех объектов на данном расстоянии в световых секундах от заданной точки.
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.android.synthetic.main.supernova.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class SupernovaeAdapter(val supernovae:List<Supernova>, val mainActivity:MainActivity):
    RecyclerView.Adapter<SupernovaeAdapter.SupernovaHolder>() {

    class SupernovaHolder(val view: ViewGroup) : RecyclerView.ViewHolder(view)

    fun getList() = supernovae

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SupernovaeAdapter.SupernovaHolder {

        val view: ViewGroup = LayoutInflater.from(parent.context).inflate(R.layout.supernova, parent, false) as ViewGroup

        return SupernovaHolder(view)
    }

    override fun onBindViewHolder(holder: SupernovaeAdapter.SupernovaHolder, position: Int) {
        val supernova = supernovae[position]
        holder.view.findViewById<TextView>(R.id.name).text = if (supernova.name!!.isNotEmpty()) supernova.name!![0] else "noname"

        // view addition info
        holder.view.setOnClickListener {
            val intent = Intent(mainActivity, ShowSupernovaActivity::class.java)
            intent.putExtra("SUPERNOVA", supernovae[position])
            mainActivity.startActivity(intent)
        }
    }
    override fun getItemCount() = supernovae.size
}


//data class WrapperProducts(
//val ar:Map<String,Map<String,Array<Supernova>>> = mapOf("GRB 060614A" to mapOf("name" to arrayOf("GRB 060614A" as Supernova)) )
//){}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = Room.databaseBuilder(
            applicationContext,
            SupernovaDatabase::class.java,
            "Supernova"
        ).build()

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_supernovae)


        // recovery
        CoroutineScope(Dispatchers.Main).launch {
            var list:List<SupernovaDB>?=null
            withContext(Dispatchers.IO) {
                list = db.supernovaDao().getAll()
            }

            val listForRecyclerView = list!!.map { Supernova().apply {
                name= arrayOf(it.name)
            } }

            recyclerView.adapter = SupernovaeAdapter(listForRecyclerView, this@MainActivity)
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)


        }


        findViewById<Button>(R.id.load).setOnClickListener {
            val ra = findViewById<EditText>(R.id.ra).text.toString()
            val dec = findViewById<EditText>(R.id.dec).text.toString()
            val radius = findViewById<EditText>(R.id.radius).text.toString()


            //Thread( Runnable {

            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.astrocats.space/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(RestSupernovae::class.java)

            val call = service.getSupernovaeByRaDecRadius(ra,dec,radius)

            call.enqueue(object : Callback<Map<String,Supernova>> {

                override fun onResponse( call: Call<Map<String,Supernova>>, response: Response<Map<String,Supernova>> ) { // <Map<String,Supernova>>

                    recyclerView.post { // передаем l-функцию, которая выполняется в потоке с вводом-выводом

                        // 1 get data
                        val list = response.body()!! // подмена сайта?

                        recyclerView.adapter = SupernovaeAdapter(list.values.toList(), this@MainActivity)
                        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)


                        // 2 save to BD
                        CoroutineScope(Dispatchers.IO).launch {

                            db.supernovaDao().clear()
                            db.supernovaDao().insert(
                                list.values.map {
                                    SupernovaDB(
                                        if (it.name!!.isNotEmpty())
                                            it.name!![0]
                                        else ""
                                    )
                                }
                            )
                        }


                    }
                }

                override fun onFailure(call: Call<Map<String,Supernova>>, t: Throwable) {
                    recyclerView.post {
                        Toast.makeText(
                            this@MainActivity,
                            "Error of load\n" + t.localizedMessage,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

            })
            //}).start()
        }

    }
}














































