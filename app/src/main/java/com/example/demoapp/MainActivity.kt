package com.example.demoapp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {
    lateinit var clickme_button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        clickme_button = findViewById(R.id.click_me)
        val mrms_obj = MRMSAndroid(false)
        clickme_button.setOnClickListener {
            Thread(Runnable {
                run {
                    try {
                        val session: String = mrms_obj.createSession()
                        Log.d("MRMS-SDK", "Session :$session")
                        val j2 = JSONObject();
                        j2.put("sid", session);
                        j2.put("aid", "10374");
                        val result2 = mrms_obj.callDeviceAPI(j2, getApplicationContext());
                        Log.d("MRMS", result2.toString());
                        val j = JSONObject()
                        j.put("Key", "651647175a87302571364113b94a0305")
                        j.put("AccountID", "2029")
                        j.put("TemplateID", "10")
                        j.put("SessionID", session)
                        j.put("Extra1", "demodata")
                        j.put("Extra2", "demodataGroup")
                        j.put("Extra3", "Red")
                        j.put("Extra4", "MAA")
                        j.put("Extra5", "AAA")
                        j.put("CustomerID", "C100012")
                        j.put("ReferenceNo", "demoTxn00136h")
                        j.put("CustomerIsReliable", "")
                        j.put("Products", "Product1:2x2500.")
                        j.put("Amount", "2793")
                        j.put("DateTime", "2014-11-17 12:13:10")
                        j.put("NameOnCard", "Jon Snow")
                        j.put("CardNumberHash", "1ff1de774005f8da13f429438815f")
                        j.put("CardNumber", "3719999350")
                        j.put("CardType", "VISA")
                        j.put("PaymentStatus", "Rejected")
                        j.put("Name", "Snow John")
                        j.put("Address", "#311, Burnside Road")
                        j.put("City", "Dagenham")
                        j.put("Region", "East London")
                        j.put("Postal", "RM81XD")
                        j.put("Country", "England")
                        j.put("CustEmail", "demo@merchantrms.com")
                        j.put("CustPhone", "9912311113")
                        j.put("ShipName", "Snow Jonn")
                        j.put("ShipAddress", "#3, Burnside Road")
                        j.put("ShipCity", "Dagenham")
                        j.put("ShipRegion", "East London");
                        j.put("ShipPostal", "RM81XD");
                        j.put("ShipEmail", "demo@merchantrms.com");
                        j.put("ShipMethod", "0");
                        j.put("ShipName", "Snow Jonn");
                        j.put("ShipPeriod", "0");
                        j.put("ShipPhone", "9912599932");

                        Log.d("MRMS", j.toString());
                        val result = mrms_obj.callAPIendpoint("Post", j, "POST");
                        Log.d("MRMS", result.toString());
                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                }
            }).start()

        }
    }
}