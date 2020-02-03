package com.damianperon.memorygamekotlin

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.damianperon.memorygamekotlin.adapters.CardsAD
import com.wajahatkarim3.easyflipview.EasyFlipView
import kotlinx.android.synthetic.main.activity_game_play_view.*
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size
import java.util.*


class GamePlayViewActivity : AppCompatActivity() {

    var random: Random? = null

    val CARDS = intArrayOf(
        R.drawable.bat,
        R.drawable.cat,
        R.drawable.cow,
        R.drawable.dog,
        R.drawable.dragon,
        R.drawable.hen,
        R.drawable.horse,
        R.drawable.man,
        R.drawable.pig,
        R.drawable.spider
    )

    var totalCards: Int? = null


    var firstCard: Int = 0
    var secondCard: Int = 0
    var fcView: EasyFlipView? = null
    var scView: EasyFlipView? = null
    var success : Int = 0;

    private lateinit var mHandler: Handler
    private lateinit var mRunnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_play_view)

        totalCards = intent.getIntExtra("totalCards", 0)
        val columns = intent.getIntExtra("columns", 0)
        success = totalCards!!.div(2)

        backButton.setOnClickListener {
            finish()
            val intent = Intent(this, LobbyViewActivity::class.java)
            startActivity(intent)
        }

        var playingCards = IntArray(totalCards!!.div(2))
        var boardCards = IntArray(totalCards!!)

        val lm: RecyclerView.LayoutManager =
            GridLayoutManager(this, columns, LinearLayoutManager.VERTICAL, false)
        cardsRV?.setLayoutManager(lm)
        cardsRV?.adapter = CardsAD(this,
            getPlayingCards(totalCards!! / 2, playingCards, boardCards),
            object : CardsAD.ItemClickListener {
                override fun onItemClick(item: EasyFlipView?, itemView: Int) {
                    validateFlip(item, itemView)
                }
            })


    }

    private fun validateFlip(view: EasyFlipView?, card: Int) {
        // Initialize the handler instance
        mHandler = Handler()
        view?.flipTheView()
        if (firstCard == 0) {
            firstCard = card
            fcView = view
        } else {
            secondCard = card
            scView = view
//            fcView?.flipTheView()
            if (!firstCard.equals(secondCard)) {
                mRunnable = Runnable {
                    fcView?.flipTheView()
                    scView?.flipTheView()
                }
                mHandler.postDelayed(
                    mRunnable,
                    1000
                )
            } else {
                success--
                fcView?.isFlipEnabled = false
                scView?.isFlipEnabled = false
                if (success == 0){
                    konfetti()
                    youWonDialog()
                }
            }
            firstCard = 0
            secondCard = 0
        }

    }

    private fun youWonDialog() {
        // Initialize a new instance of
        val builder = AlertDialog.Builder(this)

        // Set the alert dialog title
        builder.setTitle("CONGRATULATIONS!! YOU WON")

        // Display a message on alert dialog
        builder.setMessage("Do you want to play again?")

        // Set a positive button and its click listener on alert dialog
        builder.setPositiveButton("YES"){dialog, which ->
            finish()
            overridePendingTransition(0, 0)
            startActivity(getIntent())
            overridePendingTransition(0, 0)

        }


        // Display a negative button on alert dialog
        builder.setNegativeButton("Back to Menu"){dialog,which ->
            val intent = Intent(this, LobbyViewActivity::class.java)
            startActivity(intent)
        }

        // Finally, make the alert dialog using builder
        val dialog: AlertDialog = builder.create()

        // Display the alert dialog on app interface
        dialog.show()
    }

    private fun konfetti() {
        viewKonfetti.build()
            .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
            .setDirection(0.0, 359.0)
            .setSpeed(1f, 5f)
            .setFadeOutEnabled(true)
            .setTimeToLive(2000L)
            .addShapes(Shape.RECT, Shape.CIRCLE)
            .addSizes(Size(12))
            .setPosition(-50f, viewKonfetti.width + 50f, -50f, -50f)
            .streamFor(300, 5000L)
    }

    fun getPlayingCards(n: Int, playingCards: IntArray, boardCards: IntArray): IntArray {
        random = Random()
        val auxList = CARDS.toCollection(ArrayList())
        for (i in 0 until n) {
            var r = random!!.nextInt(auxList.size)
            while (playingCards?.contains(auxList[r])!!) {
                r = random!!.nextInt(auxList.size)
            }
            playingCards[i] = (auxList[r])
            boardCards[i] = (auxList[r])
            auxList.removeAt(r)
        }
        shuffle(playingCards, boardCards)

        return boardCards
    }

    fun shuffle(cards: IntArray, boardCards: IntArray) {
        val random = Random()
        var n = cards.size
        for (i in 0 until n) {
            var r = random.nextInt(n - i)
            val temp = cards[r]
            cards[r] = cards[i]
            cards[i] = temp
        }
        cards.forEachIndexed { index, card -> boardCards[index + n] = card }
    }
}
