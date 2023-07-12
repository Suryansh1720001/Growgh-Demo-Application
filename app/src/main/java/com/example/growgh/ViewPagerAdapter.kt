package com.example.growgh

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.viewpager.widget.PagerAdapter


class ViewPagerAdapter(private val context: MainActivity,

)
    : PagerAdapter() {

    private val viewReferences: MutableList<View> = mutableListOf()
    private var listener: ViewPagerAdapterListener? = null


    interface ViewPagerAdapterListener {
        fun onReadyButtonClick()
    }


    private val images = intArrayOf(
        R.drawable.image1,
        R.drawable.image2,
        R.drawable.image3,

    )

    private val headings = intArrayOf(
        R.string.heading_one,
        R.string.heading_two,
        R.string.heading_three,

    )

    private val descriptions = intArrayOf(
        R.string.desc_one,
        R.string.desc_two,
        R.string.desc_three,

    )

    override fun getCount(): Int {
        return headings.size

    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as LinearLayout
    }


    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.slider_layout, container, false)



        val slideTitleImage = view.findViewById<ImageView>(R.id.titleImage)
        val slideHeading = view.findViewById<TextView>(R.id.texttitle)
        val slideDescription = view.findViewById<TextView>(R.id.textdeccription)
        val ready = view.findViewById<ImageView>(R.id.ready)


        val next = view.findViewById<FrameLayout>(R.id.flNextButton)


        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)

        progressBar.progress =position+1

        if(position==2){
           ready.setImageResource(R.drawable.ready_imaage)
            progressBar.visibility = View.INVISIBLE;

        }else{
            slideTitleImage.setImageResource(images[position])

        }

        next.setOnClickListener {
            listener?.onReadyButtonClick()
        }




        slideHeading.setText(headings[position])
        slideDescription.setText(descriptions[position])



        container.addView(view)

        return view
    }


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }


    fun setListener(listener: ViewPagerAdapterListener) {
        this.listener = listener
    }

}

