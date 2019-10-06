package sample.app.employee.ui

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import sample.app.employee.data.Employee

/**
 */
@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<Employee>) {
    (listView.adapter as EmployeeAdapter).submitList(items)
}