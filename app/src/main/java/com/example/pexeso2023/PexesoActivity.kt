package com.example.pexeso2023


import android.content.Context
import android.graphics.Color
import android.graphics.LightingColorFilter
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pexeso2023.databaza.Score
import com.example.pexeso2023.databaza.ScoreViewModel
import java.util.Date
import java.util.Locale

class PexesoActivity : AppCompatActivity() {

    companion object{
        const val TAG= "Pexeso_Activity"
        const val BUNDLE_KEY = "game"
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
//    var bestTime = Long.MAX_VALUE
    private lateinit var bestVysledok: String
    private lateinit var obrazky: List<Karta>
    private lateinit var mScoreViewModel: ScoreViewModel
//    private var isGameOn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pexeso)

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

        var stlpce = plocha.getStlpce()
        if (portrait) stlpce=plocha.getStlpce() else plocha.riadky

        mScoreViewModel = ViewModelProvider(this).get(ScoreViewModel::class.java)

        startGame(pocetKariet, stlpce)

        if(isWon()){insertDoDb()}

//        adapter = PexesoAdapter(this, plocha, game.getObrazky(), object: KartaClickListener{
//            override fun onKartaClick(position:Int, kartaButton: ImageButton) {
//                Log.d(TAG, "poloha karty: $position")
//                updateBoard(position, kartaButton)
//            }
//        })
//
//        hraciaPlocha.adapter=adapter
//        hraciaPlocha.setHasFixedSize(true)
//        hraciaPlocha.layoutManager = GridLayoutManager(this, stlpce)

    }

//    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
//        super.onSaveInstanceState(outState, outPersistentState)
//        outState.putSerializable(BUNDLE_KEY, game)
//    }

    private fun startGame(pocetKariet: Int, stlpce:Int){
        startTime=SystemClock.elapsedRealtime()
//        isGameOn=true
        game = PexesoGame(pocetKariet,this)
        adapter = PexesoAdapter(this, plocha, game.getObrazky(), object: KartaClickListener{
            override fun onKartaClick(position:Int, kartaButton: ImageButton) {
                Log.d(TAG, "poloha karty: $position")
                updateBoard(position, kartaButton)
            }
        })

        hraciaPlocha.adapter=adapter
        hraciaPlocha.setHasFixedSize(true)
        hraciaPlocha.layoutManager = GridLayoutManager(this, stlpce)

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
//            bestTime = updateBestTime(yourTime)
            bestVysledok = updateBestTime(yourTime)
            zobrazDialog(yourTime)
        }
    }

    private fun isWon():Boolean{
        return uhadnutePary==(pocetKariet/2)
    }

    private fun updateBestTime(time:Long):String{
//        val pref = getPreferences(MODE_PRIVATE)
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
//        Log.d(TAG, "bestTime $bestTime")
        Log.d(TAG, "vysledok: $bestVysledok")
        val dialog = DialogVyhra(this, konvertujCas(yourTime), bestVysledok)
        dialog.showDialog()
    }
    fun konvertujCas(ms: Long):String{
        val min = ms / 1000 / 60
        val sec = ms / 1000 % 60
        return "$min:$sec"
    }
    private fun insertDoDb(){
        val datum = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
//        var scoreDao= ScoreDatabase.getDatabase(this).scoreDao()
//        val scoreRepo = ScoreRepo(scoreDao)
        val score = Score(null,datum, "1:12", "easy")
//        scoreRepo.upsertScore(score)
        mScoreViewModel.upsertScore(score)
    }

}


