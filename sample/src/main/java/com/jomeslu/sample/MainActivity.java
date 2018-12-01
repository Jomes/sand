package com.jomeslu.sample;

import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.LinearInterpolator;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import jomeslu.com.sandpic.SandPic;

public class MainActivity extends AppCompatActivity {
    private ImageView pic;
    private SeekBar seekbarPointNum;
    private SeekBar seekbarThreshold;
    private Bitmap mImageBitmap;
    private int mLastProgress;
    public final int SEEKBAR_STOP_CHANGE_DELTA = 5;
    private TextView desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViews();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 3;
        mImageBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fei, options);
        pic.setImageBitmap(mImageBitmap);

        int w = mImageBitmap.getWidth();
        int h = mImageBitmap.getHeight();
        int maxPoint = 1 * w * h / 2;
        maxPoint = maxPoint > 100000 ? 100000 : maxPoint;
        seekbarPointNum.setMax(maxPoint);
        seekbarPointNum.setOnSeekBarChangeListener(changeListener);
        seekbarThreshold.setOnSeekBarChangeListener(changeListener);

        desc.setText(getString(R.string.action_decs, String.valueOf(seekbarThreshold.getProgress()), String.valueOf(seekbarPointNum.getProgress())));

    }

    private void findViews() {
        pic = (ImageView) findViewById(R.id.pic);
        seekbarPointNum = (SeekBar) findViewById(R.id.seekbar_point_num);
        seekbarThreshold = (SeekBar) findViewById(R.id.seekbar_threshold);
        desc = (TextView) findViewById(R.id.desc);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            ObjectAnimator animator = ObjectAnimator.ofInt(seekbarPointNum, "progress", 0, seekbarPointNum.getMax());
            animator.setInterpolator(new LinearInterpolator());
            animator.setDuration(15000);
            animator.start();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private SeekBar.OnSeekBarChangeListener changeListener = new SeekBar.OnSeekBarChangeListener() {


        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (Math.abs(seekBar.getProgress() - mLastProgress) > SEEKBAR_STOP_CHANGE_DELTA) {
                mLastProgress = seekbarPointNum.getProgress();
                if (!isProess) {
                    progressChanged();
                }
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            progressChanged();
        }
    };
    private boolean isProess = false;

    private void progressChanged() {
        mLastProgress = seekbarPointNum.getProgress();
        desc.setText(getString(R.string.action_decs, String.valueOf(seekbarThreshold.getProgress()), String.valueOf(seekbarPointNum.getProgress())));
        if (!isProess) {
            SandAsyncTask task = new SandAsyncTask();
            int pointNum = seekbarPointNum.getProgress();
            int thre = seekbarThreshold.getProgress();
            task.execute(pointNum, thre, mImageBitmap);
        }

    }

    private class SandAsyncTask extends AsyncTask<Object, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(Object... params) {
            int pointNum = (int) params[0];
            int thre = (int) params[1];
            Bitmap originalBitmap = (Bitmap) params[2];
            return SandPic.getInstance().tramform(originalBitmap, thre, pointNum);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            pic.setImageBitmap(result);
            isProess = false;
        }

        @Override
        protected void onPreExecute() {

            isProess = true;
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }
    }
}
