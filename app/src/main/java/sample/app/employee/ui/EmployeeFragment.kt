package sample.app.employee.ui


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerFragment
import sample.app.databinding.FragmentEmployeeBinding
import sample.app.util.setupRefreshLayout
import timber.log.Timber
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class EmployeeFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<EmployeeViewModel> { viewModelFactory }

    private lateinit var viewDataBinding: FragmentEmployeeBinding

    private lateinit var listAdapter: EmployeeAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        viewDataBinding = FragmentEmployeeBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
        }

        setHasOptionsMenu(true)

        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Set the lifecycle owner to the lifecycle of the view
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setupListAdapter()
        setupRefreshLayout(viewDataBinding.refreshLayout, viewDataBinding.tasksList)
        // Always reloading data for simplicity. Real apps should only do this on first load and
        // when navigating back to this destination. TODO: https://issuetracker.google.com/79672220
        viewModel.loadTasks()
    }

    private fun setupListAdapter() {
        val viewModel = viewDataBinding.viewmodel
        Log.d("test", "adapter setupListAdapter....")

        if (viewModel != null) {
            listAdapter = EmployeeAdapter(viewModel)
            viewDataBinding.tasksList.adapter = listAdapter
            Log.d("test", "adapter set....")
        } else {
            Timber.w("ViewModel not initialized when attempting to set up adapter.")
        }
    }

}
