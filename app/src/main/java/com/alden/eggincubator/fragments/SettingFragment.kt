        package com.alden.eggincubator.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.alden.eggincubator.Activity.SystemOffActivity
import com.alden.eggincubator.R
import com.alden.eggincubator.databinding.FragmentSettingBinding
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


        private const val TAG = "SettingFragment"

class SettingFragment : Fragment() {
    val fbdb = FirebaseDatabase.getInstance()
    var lampStatus: Boolean = false
    lateinit var lampListener: ValueEventListener
    lateinit var binding: FragmentSettingBinding
    val lampRef = fbdb.getReference("FirebaseIOT")
    val settingRef = fbdb.getReference("SettingData")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        initLampStatus()

        binding.cvReboot.setOnClickListener {
            popUpUniverse(
                "reset",
                "Mulai Ulang",
                "Apa kamu yakin mau mulai ulang\n" +
                        "alat penetas telur?"
            )
        }

        binding.cvShutDown.setOnClickListener {
            popUpUniverse(
                "shutdown",
                "Matikan Alat",
                "Apa kamu yakin mau mematikan\n" +
                        "alat penetas telur?"
            )

        }

//        binding.cvLamp.setOnClickListener {
//            if(lampStatus){
//                popUpUniverse(
//                    "lampOff",
//                    "Matikan Lampu",
//                    "Apa kamu yakin mau mematikan lampu\n" +
//                            "alat penetas telur?"
//                )
//            }else {
//                popUpUniverse(
//                    "lampOn",
//                    "Hidupkan Lampu",
//                    "Hidupkan lampu untuk menyinari duniamu mwehehehe"
//                )
//            }
//        }
    }

//    private fun initLampStatus() {
//       lampListener =  lampRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                var data = snapshot.child("lampu1").value.toString()
//                setLampStatus(data)
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.w(TAG, "onCancelled: ", error.toException())
//            }
//        })
//    }

//    private fun setLampStatus(data: String) {
//        lampStatus = data == "1"
//        if (lampStatus){
//            binding.tvLampStatus.text ="Matikan Lampu"
//        }else{
//            binding.tvLampStatus.text ="Hidupkan Lampu"
//        }
//    }


    private fun popUpUniverse(command: String, title: String, subTitle: String) {
        //initialize View
        val vView: View = LayoutInflater.from(context).inflate(R.layout.popup_reboot, null, false)
        val btnOk: Button = vView.findViewById(R.id.btnPopUpOk)
        val btnCancel: Button = vView.findViewById(R.id.btnPopUpCancel)
        val tvTitle: TextView = vView.findViewById(R.id.tvPopUpTitle)
        val tvSubTitle: TextView = vView.findViewById(R.id.tvPopUpSubTitle)

        //initialize text
        tvTitle.text = title
        tvSubTitle.text = subTitle

        val universeAlert = AlertDialog.Builder(context, R.style.CustomAlertDialog)
            .setView(vView)
            .setCancelable(false)
        val universeAlertDialog = universeAlert.show()

        btnOk.setOnClickListener {
            fowardAction(command)
            universeAlertDialog.dismiss()
        }

        btnCancel.setOnClickListener {
            universeAlertDialog.cancel()
        }
    }


    private fun fowardAction(param: String) {
        when (param) {
            "lampOff" -> changeLampStatus(true)
            "lampOn" -> changeLampStatus(false)
            "shutdown" -> actionShutdown()
            "reset" -> actionReset()
            else -> actionToast()
        }
    }


    private fun changeLampStatus(wantDead: Boolean) {
        if (wantDead) {
            fbdb.getReference("FirebaseIOT").child("lampu1").setValue("0")
        } else {
            fbdb.getReference("FirebaseIOT").child("lampu1").setValue("1")
        }


    }

    private fun actionShutdown() {
        settingRef.child("isShutdown").setValue(1).addOnCompleteListener {
//            val intent = Intent(view!!.context, SystemOffActivity::class.java)
//            view!!.context.startActivity(intent)
            activity!!.finish()
        }

    }

    private fun actionReset() {
        settingRef.child("isReset").setValue(1)
        settingRef.child("isStart").setValue(1)
        activity?.finish()
    }


    private fun actionToast() {
        Toast.makeText(context, "Something wrong", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        lampRef.removeEventListener(lampListener)
    }
}
