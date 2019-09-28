package sample.app.employee


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import sample.app.R

/**
 * A simple [Fragment] subclass.
 */
class EmployeeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_employee, container, false)

//        v.findViewById<Button>(R.id.skip).setOnClickListener {
//            findNavController().navigate(EmployeeFragmentDirections.actionEmployeeFragmentToTasksFragment())
//        }

        return v
    }


}
