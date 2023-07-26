package com.configoo.smartjewellerin.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.configoo.smartjewellerin.R
import com.configoo.smartjewellerin.activity.PaymentActivity
import com.configoo.smartjewellerin.helper.ApiConfig.Companion.getDayOfWeek
import com.configoo.smartjewellerin.helper.ApiConfig.Companion.getMonth
import com.configoo.smartjewellerin.helper.Constant
import com.configoo.smartjewellerin.model.BookingDate

/**
 * Created by shree1 on 3/16/2017.
 */
class DateAdapter(val activity: Activity, private val bookingDates: ArrayList<BookingDate>) :
    RecyclerView.Adapter<DateAdapter.ItemHolder>() {
    override fun getItemCount(): Int {
        return bookingDates.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val bookingDate = bookingDates[position]
        if (Constant.selectedDatePosition == position) {
            PaymentActivity.deliveryDay = bookingDate.date + "-" + getMonth(
                activity,
                bookingDate.month.toInt()
            ) + "-" + bookingDate.year
            holder.relativeLyt.setBackgroundResource(R.drawable.selected_date_shadow)
            holder.tvDay.setTextColor(ContextCompat.getColor(activity, R.color.white))
            holder.tvDate.setTextColor(ContextCompat.getColor(activity, R.color.white))
            holder.tvMonth.setTextColor(ContextCompat.getColor(activity, R.color.white))
        } else {
            holder.tvDay.setTextColor(ContextCompat.getColor(activity, R.color.gray))
            holder.tvDate.setTextColor(ContextCompat.getColor(activity, R.color.gray))
            holder.tvMonth.setTextColor(ContextCompat.getColor(activity, R.color.gray))
            holder.relativeLyt.setBackgroundResource(R.drawable.date_shadow)
        }
        holder.relativeLyt.setPadding(
            activity.resources.getDimension(R.dimen._15sdp).toInt(),
            activity.resources.getDimension(R.dimen._15sdp)
                .toInt(),
            activity.resources.getDimension(R.dimen._15sdp).toInt(),
            activity.resources.getDimension(R.dimen._15sdp)
                .toInt()
        )
        holder.tvDay.text = getDayOfWeek(activity, bookingDate.day.toInt())
        holder.tvDate.text = bookingDate.date
        holder.tvMonth.text = getMonth(activity, bookingDate.month.toInt())
        holder.relativeLyt.setOnClickListener {
            if (PaymentActivity.deliveryDay.isNotEmpty()) {
                Constant.selectedDatePosition = position
                notifyDataSetChanged()
                PaymentActivity.deliveryTime = ""
                PaymentActivity.adapter.notifyDataSetChanged()
                PaymentActivity.recyclerView.adapter = PaymentActivity.adapter
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lyt_date, parent, false)
        return ItemHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val tvMonth: TextView = itemView.findViewById(R.id.tvMonth)
        val tvDay: TextView = itemView.findViewById(R.id.tvDay)
        val relativeLyt: RelativeLayout = itemView.findViewById(R.id.relativeLyt)

    }
}