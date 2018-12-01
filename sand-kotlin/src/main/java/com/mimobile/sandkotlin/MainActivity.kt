package com.mimobile.sandkotlin

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private var pic: ImageView? = null
    private var seekbarPointNum: SeekBar? = null
    private var seekbarThreshold: SeekBar? = null
    private var mImageBitmap: Bitmap? = null
    private var mLastProgress: Int = 0
    val SEEKBAR_STOP_CHANGE_DELTA = 5
    private var desc: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val toolbar = findViewById<Toolbar>(R.id.toolbar) as Toolbar
//        setSupportActionBar(toolbar)
        findViews()
        val options = BitmapFactory.Options()
        options.inSampleSize = 3
        mImageBitmap = BitmapFactory.decodeResource(resources, R.drawable.fei, options)
        pic!!.setImageBitmap(mImageBitmap)

        val w = mImageBitmap!!.width
        val h = mImageBitmap!!.height
        var maxPoint = 1 * w * h / 2
        maxPoint = if (maxPoint > 100000) 100000 else maxPoint
        seekbarPointNum!!.max = maxPoint
        seekbarPointNum!!.setOnSeekBarChangeListener(changeListener)
        seekbarThreshold!!.setOnSeekBarChangeListener(changeListener)

        desc!!.text = getString(R.string.action_decs, seekbarThreshold!!.progress.toString(), seekbarPointNum!!.progress.toString())

    }

    private fun findViews() {
        pic = findViewById(R.id.pic)
        seekbarPointNum = findViewById(R.id.seekbar_point_num)
        seekbarThreshold = findViewById(R.id.seekbar_threshold)
        desc = findViewById(R.id.desc)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    @SuppressLint("ObjectAnimatorBinding")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == R.id.action_settings) {
            val animator = ObjectAnimator.ofInt(seekbarPointNum, "progress", 0, seekbarPointNum!!.max)
            animator.interpolator = LinearInterpolator()
            animator.duration = 15000
            animator.start()

            return true
        }

        return super.onOptionsItemSelected(item)
    }


    private val changeListener = object : SeekBar.OnSeekBarChangeListener {


        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            if (Math.abs(seekBar.progress - mLastProgress) > SEEKBAR_STOP_CHANGE_DELTA) {
                mLastProgress = seekbarPointNum!!.progress
                if (!isProess) {
                    progressChanged()
                }
            }
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {

        }

        override fun onStopTrackingTouch(seekBar: SeekBar) {
            progressChanged()
        }
    }
    private var isProess = false

    private fun progressChanged() {
        mLastProgress = seekbarPointNum!!.progress
        desc!!.text = getString(R.string.action_decs, seekbarThreshold!!.progress.toString(), seekbarPointNum!!.progress.toString())
        if (!isProess) {
            val task = SandAsyncTask()
            val pointNum = seekbarPointNum!!.progress
            val thre = seekbarThreshold!!.progress
            task.execute(pointNum, thre, mImageBitmap)
        }

    }

    private inner class SandAsyncTask : AsyncTask<Any, Void, Bitmap>() {

        override fun doInBackground(vararg params: Any): Bitmap {
            val pointNum = params[0] as Int
            val thre = params[1] as Int
            val originalBitmap = params[2] as Bitmap
            return SandPic.instance.tramform(originalBitmap, thre, pointNum)
        }

        override fun onPostExecute(result: Bitmap) {
            pic!!.setImageBitmap(result)
            isProess = false
        }

        override fun onPreExecute() {

            isProess = true
        }

        override fun onProgressUpdate(vararg values: Void) {

        }
    }

}
