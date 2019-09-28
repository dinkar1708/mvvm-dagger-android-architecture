/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sample.app.employee.ui

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import sample.app.data.Result.Success
import sample.app.data.source.TasksDataSource
import sample.app.employee.data.Employee
import sample.app.employee.data.EmployeeRepository
import javax.inject.Inject

/**
 * ViewModel for the task list screen.
 */
class EmployeeViewModel @Inject constructor(
        private val tasksRepository: EmployeeRepository
) : ViewModel() {

    private val _items = MutableLiveData<List<Employee>>().apply { value = emptyList() }
    val items: LiveData<List<Employee>> by lazy { _items }

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _noTasksLabel = MutableLiveData<Int>()
    val noTasksLabel: LiveData<Int> = _noTasksLabel

    // This LiveData depends on another so we can use a transformation.
    val empty: LiveData<Boolean> = Transformations.map(_items) {
        it.isEmpty()
    }

    init {
        loadTasks()
    }

    /**
     * @param forceUpdate   Pass in true to refresh the data in the [TasksDataSource]
     */
    fun loadTasks() {
        Log.d("test", "loading.....emp")
        _dataLoading.value = true

//        wrapEspressoIdlingResource {

        viewModelScope.launch {
            val tasksResult = tasksRepository.getEmployees()
            Log.d("test", "loading.....$tasksResult")

            if (tasksResult is Success) {
                val tasks = tasksResult.data
                _items.value = tasks
            } else {
//                    _noTasksLabel.value =  R.string.no_employee_data
            }
            _dataLoading.value = false
        }
//        }
    }

    /**
     * refresh call
     */
    fun refresh() {
        loadTasks()
    }
}
