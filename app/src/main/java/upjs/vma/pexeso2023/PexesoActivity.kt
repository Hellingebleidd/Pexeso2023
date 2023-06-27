package upjs.vma.pexeso2023


import android.content.Context
import android.graphics.Color
import android.graphics.LightingColorFilter
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.util.Log
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import upjs.vma.pexeso2023.databaza.Score
import upjs.vma.pexeso2023.databaza.ScoreDatabase
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class PexesoActivity : AppCompatActivity() {

    companion object{
        const val TAG= "Pexeso_Activity"
    }

    private lateinit var hraciaPlocha : RecyclerView
    private var pocetKariet = 0
    private lateinit var plocha: Plocha
    private lateinit var adapter: PexesoAdapter
    private lateinit var game: Game
    var positionPrvejKarty = 0
    var positionDruhejKarty = 0
    private lateinit var button1:ImageButton
    private lateinit var button2:ImageButton
    var portrait = true;
    var pocetOtocenych:Int=0
    var uhadnutePary = 0
    var startTime:Long=0
    private lateinit var bestVysledok: String
    private lateinit var obrazky: List<Karta>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pexeso)

        supportActionBar?.title="Main menu"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        hraciaPlocha = findViewById(R.id.hraciaPlocha)

        val intent = intent
        pocetKariet = intent.getIntExtra("difficulty", 0)
        Log.d("HRA", "pocet parov kariet: $pocetKariet")

        when(resources.configuration.orientation){
            1->{//portrait
                portrait = true
            }
            2->{//landscape
                portrait = false
            }}

        plocha = Plocha(pocetKariet, portrait)

        pripravPlochu()
        startGame()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home ->{
                showAlertDialog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onBackPressed() {
        showAlertDialog()
    }
    private fun showAlertDialog(){
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Confirmation")
        alertDialogBuilder.setMessage("Are you sure you want to go back?")
        alertDialogBuilder.setPositiveButton("Yes") { dialog, _ ->
//            if(where=="actionBar"){
//                supportActionBar?.setDisplayHomeAsUpEnabled(false)
//            }
            super.onBackPressed()
            dialog.dismiss()
        }
        alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        alertDialogBuilder.create().show()
    }

    private fun pripravPlochu(){
        game= PexesoGame(pocetKariet,this)
        adapter = PexesoAdapter(this, plocha, game.getObrazky(), object: KartaClickListener{
            override fun onKartaClick(position:Int, kartaButton: ImageButton) {
                Log.d(TAG, "poloha karty: $position")
                updateBoard(position, kartaButton)
            }
        })
        hraciaPlocha.adapter=adapter
        hraciaPlocha.layoutManager = GridLayoutManager(this, plocha.getStlpce())

    }

    private fun startGame(){
        startTime=SystemClock.elapsedRealtime()
        obrazky=game.getObrazky()
    }

    private fun updateBoard(position:Int, kartaButton: ImageButton) {
        when(pocetOtocenych){
            0->{
                otocKartu(position, kartaButton)
                positionPrvejKarty=position
                button1=kartaButton
                button1.isEnabled=false
            }
            1->{
                otocKartu(position, kartaButton)
                positionDruhejKarty = position
                button2=kartaButton

                Handler().postDelayed({
//                    game.choosePair(positionPrvejKarty, button1 ,positionDruhejKarty, button2)
                      isPair(positionPrvejKarty, button1, positionDruhejKarty,button2)
                },700)
            }
            2->{
                Toast.makeText(this, "Invalid move", Toast.LENGTH_LONG).show()
            }
        }
        Log.d(TAG, "otocene karty: ${game.otoceneKarty}")
    }

    private fun otocKartu(position: Int, button: ImageButton){
        var karta = obrazky[position]
        Log.d(PexesoGame.TAG, "otacam kartu na pozicii $position")
        Log.d(PexesoGame.TAG, "otacam kartu s obrazkom ${karta.obrazok}")

        pocetOtocenych+=1

        Log.d(PexesoGame.TAG, "otocene karty: $pocetOtocenych")

        if(karta.vidnoObrazok){
            button.animate().apply {
                duration= PexesoGame.RYCHLOST
                rotationYBy(-180f)
            }.start()
            button.postDelayed({button.setImageResource(android.R.color.holo_green_light)}, PexesoGame.RYCHLOST /2)
            karta.vidnoObrazok=false
        }else{
            button.animate().apply {
                duration= PexesoGame.RYCHLOST
                rotationYBy(180f)
            }.start()
            button.postDelayed({button.setImageResource(karta.obrazok)}, PexesoGame.RYCHLOST /2)
            karta.vidnoObrazok=true
        }
    }
    private fun isPair(position1: Int,
                       button1: ImageButton,
                       position2: Int,
                       button2: ImageButton){
        var karta1 = obrazky[position1]
        var karta2 = obrazky[position2]
        var foundPair=false
        Log.d(PexesoGame.TAG, "prva karta obr: ${karta1.obrazok}")
        Log.d(PexesoGame.TAG, "druha karta obr: ${karta2.obrazok}")
        if(karta1.obrazok==karta2.obrazok){
            foundPair = true
            karta1.maPar=true
            karta2.maPar=true
            //disabluju sa
            button1.isEnabled=false
//            button1.isVisible=false
            button1.colorFilter = LightingColorFilter(Color.GRAY, Color.BLACK)
            button2.isEnabled=false
//            button2.isVisible=false
            button2.colorFilter = LightingColorFilter(Color.GRAY, Color.BLACK)
            uhadnutePary+=1
        }else{
            //sa otocia nazad
            button1.isEnabled=true
            otocKartu(position1,button1)
            otocKartu(position2,button2)

        }
        Log.d(PexesoGame.TAG, "found pair: $foundPair")
        Log.d(PexesoGame.TAG,"uhadnute pary: $uhadnutePary / ${pocetKariet/2}")
        pocetOtocenych=0

        if(isWon()){
            var yourTime = SystemClock.elapsedRealtime()-startTime
            bestVysledok = updateBestTime(yourTime)
            addScore(konvertujCas(yourTime))
            zobrazDialog(yourTime)
        }
    }

    private fun addScore(time:String){
        val datum= (LocalDate.now()).format(DateTimeFormatter.ofPattern("dd.MMMM")) //toString() //.substring(0,10)
        val time = time
        var obtiaznost = ""
        when(pocetKariet){
            EASY_GAME_CARDS -> obtiaznost="easy"
            MEDIUM_GAME_CARDS -> obtiaznost="medium"
            HARD_GAME_CARDS -> obtiaznost = "hard"
        }

        lifecycleScope.launch {
            val score = Score(datum, time, obtiaznost)
            ScoreDatabase(this@PexesoActivity).scoreDao().upsertScore(score  )
        }
    }

    private fun isWon():Boolean{
        return uhadnutePary==(pocetKariet/2)
    }
    private fun updateBestTime(time:Long):String{
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        var obtiaznost = ""
        when(pocetKariet){
            EASY_GAME_CARDS -> obtiaznost="easy"
            MEDIUM_GAME_CARDS -> obtiaznost="medium"
            HARD_GAME_CARDS -> obtiaznost = "hard"
        }
        editor.putString("obtiaznost", obtiaznost).apply()
        val bestTime = sharedPreferences.getLong("best_time_$obtiaznost", Long.MAX_VALUE)
        if(time<bestTime){
            editor.putLong("best_time_$obtiaznost", time).apply()
        }
        return (konvertujCas(sharedPreferences.getLong("best_time_$obtiaznost", time)) + ", " + sharedPreferences.getString("obtiaznost", obtiaznost))

    }
    private fun zobrazDialog(yourTime: Long){
        Log.d(TAG,"yourTime $yourTime")
        Log.d(TAG, "vysledok: $bestVysledok")
        val dialog = DialogVyhra(this, konvertujCas(yourTime), bestVysledok)
        dialog.showDialog()
    }
    fun konvertujCas(ms: Long):String{
        val min = ms / 1000 / 60
        val sec = ms / 1000 % 60
        val sekundy = ("0000"+sec).takeLast(2)
        return "$min:$sekundy"
    }


}


