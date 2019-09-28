package sample.app.employee.data

data class Employee @JvmOverloads constructor(
        val id: String,
        val employee_name: String,
        val employee_salary: String,
        val employee_age: String,
        val profile_image: String
)