package vsl.bhavesh.jsonfiledemo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Insert OnClick  [ START ]
        insert.setOnClickListener {

            var fis = openFileInput("employees.json") // if file already avaliable
            var g = Gson()
            var emps:Employees? = null
            //var list:MutableList<Employees>? = null

            var list:MutableList<Employee>? = null
            // check if file has any data
            if(fis.available() > 0){

                var reader = InputStreamReader(fis)
                var emps = g.fromJson(reader,Employees::class.java)
                list = emps.employees
            }else{
                // Create object foe employee class
                list  = mutableListOf<Employee>()
            }
            // take the input value
            var et1Val = et1.text.toString().toInt()
            var et2Val = et2.text.toString()
            var et3Val = et3.text.toString()
            var et4Val = et4.text.toString()

            var e = Employee(et1Val,et2Val,et3Val,et4Val)

            // Add individual employee object into list
            list.add(e)  // list is point to Array

            emps = Employees(list)  // this point to main object to Employees




            //Convert Object format to JSON using toJSON  - add 3rd party library
            //var g = Gson()
            var json_response = g.toJson(emps) //return is string format
            // Now this response we will write on file

            var fos = openFileOutput("employees.json", Context.MODE_PRIVATE)

            fos.write(json_response.toByteArray()) // convert from string to ByteArray to write on file
            // first store in buffer then after it write on the acutal file
            fos.flush()
            fos.close()

//Device File Explorer >> Data >> Data >> Pkg Name >> employees.json

        }
        // Insert OnClick  [ END ]

        // Read OnClick
        read.setOnClickListener {

            var g = Gson()
            var fis = openFileInput("employees.json")
            var reader = InputStreamReader(fis)
            var emps = g.fromJson(reader,Employees::class.java)

            var list = emps.employees
            var temp_list = mutableListOf<String>()

            for(e in list){

                var eidVal = e.id.toString()
                var enameVal = e.name.toString()
                var edesiVal = e.desi.toString()
                var edeptVal = e.dept.toString()

                temp_list.add(eidVal + "\t" + enameVal + "\n" + edesiVal + "\t" + edeptVal)
            }

            // Now display the temp_list into Array adapter
            var adapter = ArrayAdapter<String>(this@MainActivity,
                    android.R.layout.simple_list_item_single_choice,temp_list)
            lview.adapter = adapter

        }



    } // onCreate
} // MainActivity
