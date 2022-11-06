package com.suvarnatechlabs.lottoaipredictor.activities

import android.app.Dialog
import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet.GONE
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.suvarnatechlabs.lottoaipredictor.*
import com.suvarnatechlabs.lottoaipredictor.adapter.FiveNumberAdapter
import com.suvarnatechlabs.lottoaipredictor.adapter.MyNumbersAdapter
import com.suvarnatechlabs.lottoaipredictor.adapter.SevenNumberAdapter
import com.suvarnatechlabs.lottoaipredictor.adapter.SixNumberAdapter
import com.suvarnatechlabs.lottoaipredictor.databinding.*
import com.suvarnatechlabs.lottoaipredictor.interfaceAndListeners.RecyclerItemClickListenr
import com.suvarnatechlabs.lottoaipredictor.model.FiveNumber
import com.suvarnatechlabs.lottoaipredictor.model.SevenNumber
import com.suvarnatechlabs.lottoaipredictor.model.SixNumber
import com.suvarnatechlabs.lottoaipredictor.preferences.GooglePlayBillingPreferences
import com.android.billingclient.api.*
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import java.math.BigInteger

class LottoActivity : BaseActivity(), View.OnClickListener {
    private lateinit var context: Context
    private lateinit var binding: ActivityLottoBinding
    private lateinit var dialogBinding: LayoutDialogNumbersBinding
    private lateinit var dialog: Dialog
    private var selectedNumber = ArrayList<String>()
    private val listOfFirst5Cols = listOf<String>(
        "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40",
        "41", "42", "43", "44", "45", "46", "47", "48", "49", "50",
        "51", "52", "53", "54", "55", "56", "57", "58", "59", "60",
        "61", "62", "63", "64", "65", "66", "67", "68", "69", "70",
        "71", "72", "73", "74", "75", "76", "77", "78", "79", "80",
        "81", "82", "83", "84", "85", "86", "87", "88", "89", "90"
    )
    private val listOfColumns = listOf<String>("5", "6", "7")

    private var sumCol17: Int? = 0
    private var sumCol28: Int? = 0
    private var sumCol39: Int? = 0
    private var sumCol410: Int? = 0
    private var sumCol511: Int? = 0
    private var sumCol612: Int? = 0

    private var sumCol16: Int? = 0
    private var sumCol27: Int? = 0
    private var sumCol38: Int? = 0
    private var sumCol49: Int? = 0
    private var sumCol510: Int? = 0

    private var sumCol18: Int? = 0
    private var sumCol29: Int? = 0
    private var sumCol310: Int? = 0
    private var sumCol411: Int? = 0
    private var sumCol512: Int? = 0
    private var sumCol613: Int? = 0
    private var sumCol714: Int? = 0

    private var mInterstitialAd: InterstitialAd? = null
    private final var TAG = "MainActivity"
    private lateinit var billingClient: BillingClient
    lateinit var skulList: ArrayList<String>
    private lateinit var fbInterstitialAdd: InterstitialAd
    private lateinit var layoutSeven: View
    private lateinit var layoutSix: View
    private lateinit var layoutFive: View
    private lateinit var dynamicCol: RelativeLayout
    private lateinit var layoutColFiveBinding: LayoutColFiveBinding
    private lateinit var layoutColSixBinding: LayoutColSixBinding
    private lateinit var layoutColSevenBinding: LayoutColSevenBinding
    private lateinit var view: View

    private var sixNumberList : ArrayList<SixNumber> = ArrayList<SixNumber>()
    private var sevenNumberList : ArrayList<SevenNumber> = ArrayList<SevenNumber>()
    private var fiveNumberList : ArrayList<FiveNumber> = ArrayList<FiveNumber>()

    private lateinit var fiveNumberAdapter : FiveNumberAdapter
    private lateinit var sixNumberAdapter : SixNumberAdapter
    private lateinit var sevenNumberAdapter : SevenNumberAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLottoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        view = binding.root
        context = this
        layoutSeven = findViewById(R.id.layout_seven)
        layoutSix = findViewById(R.id.layout_six)
        layoutFive = findViewById(R.id.layout_five)
        dynamicCol = findViewById(R.id.rl_dynamic_cols)
        dynamicCol.visibility = View.GONE
        layoutColFiveBinding = LayoutColFiveBinding.bind(view)
        layoutColSixBinding = LayoutColSixBinding.bind(view)
        layoutColSevenBinding = LayoutColSevenBinding.bind(view)


        MobileAds.initialize(this)
        binding.bannerAdview.loadAd(AdRequest.Builder().build())



        binding.recyclerview6column.layoutManager = LinearLayoutManager(this)

        binding.llColNo.setOnClickListener(this)
        binding.llMaxNo.setOnClickListener(this)

        layoutColFiveBinding.llTv15.setOnClickListener(this)
        layoutColFiveBinding.llTv25.setOnClickListener(this)
        layoutColFiveBinding.llTv35.setOnClickListener(this)
        layoutColFiveBinding.llTv45.setOnClickListener(this)
        layoutColFiveBinding.llTv55.setOnClickListener(this)
        layoutColFiveBinding.llTv65.setOnClickListener(this)
        layoutColFiveBinding.llTv75.setOnClickListener(this)
        layoutColFiveBinding.llTv85.setOnClickListener(this);
        layoutColFiveBinding.llTv95.setOnClickListener(this)
        layoutColFiveBinding.llTv105.setOnClickListener(this)

        layoutColSixBinding.llTv16.setOnClickListener(this)
        layoutColSixBinding.llTv26.setOnClickListener(this)
        layoutColSixBinding.llTv36.setOnClickListener(this)
        layoutColSixBinding.llTv46.setOnClickListener(this)
        layoutColSixBinding.llTv56.setOnClickListener(this)
        layoutColSixBinding.llTv66.setOnClickListener(this)
        layoutColSixBinding.llTv76.setOnClickListener(this)
        layoutColSixBinding.llTv86.setOnClickListener(this)
        layoutColSixBinding.llTv96.setOnClickListener(this)
        layoutColSixBinding.llTv106.setOnClickListener(this)
        layoutColSixBinding.llTv116.setOnClickListener(this)
        layoutColSixBinding.llTv126.setOnClickListener(this)

        layoutColSevenBinding.llTv17.setOnClickListener(this)
        layoutColSevenBinding.llTv27.setOnClickListener(this)
        layoutColSevenBinding.llTv37.setOnClickListener(this)
        layoutColSevenBinding.llTv47.setOnClickListener(this)
        layoutColSevenBinding.llTv57.setOnClickListener(this)
        layoutColSevenBinding.llTv67.setOnClickListener(this)
        layoutColSevenBinding.llTv77.setOnClickListener(this)
        layoutColSevenBinding.llTv87.setOnClickListener(this)
        layoutColSevenBinding.llTv97.setOnClickListener(this)
        layoutColSevenBinding.llTv107.setOnClickListener(this)
        layoutColSevenBinding.llTv117.setOnClickListener(this)
        layoutColSevenBinding.llTv127.setOnClickListener(this)
        layoutColSevenBinding.llTv137.setOnClickListener(this)
        layoutColSevenBinding.llTv147.setOnClickListener(this)

        binding.btnGen2nos.setOnClickListener(this)
        binding.btnGen40nos.setOnClickListener(this)

        if (!GooglePlayBillingPreferences.isPurchased()) {
            binding.btnGen40nos.text = "Generate 40's Lines/Rows(Paid Version)"
        } else {
            binding.btnGen40nos.text = "Generate 40's Lines/Rows"
        }

        val purchasesUpdatedListener =
            PurchasesUpdatedListener { billingResult, purchases ->
                if(billingResult.responseCode == BillingClient.BillingResponseCode.OK){
                    if (purchases != null) {
                        handlePurchases(purchases)
                    }
                    GooglePlayBillingPreferences.setPurchased(true)
                    binding.btnGen40nos.text = "Generate 40's Lines/Rows"
                }else if(billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED){
                    GooglePlayBillingPreferences.setPurchased(false)
                    binding.btnGen40nos.text = "Generate 40's Lines/Rows(Paid Version)"
                }
            }

        billingClient = BillingClient.newBuilder(context)
            .setListener(purchasesUpdatedListener)
            .enablePendingPurchases()
            .build()

        skulList = ArrayList<String>()
        skulList.add("lottoaipredictor")

        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    // The BillingClient is ready. You can query purchases here.
                }
            }

            override fun onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        })

    }

    override fun onClick(view: View?) {
        dialogBinding = LayoutDialogNumbersBinding.inflate(layoutInflater)
        dialogBinding.rvNumbers.layoutManager = GridLayoutManager(this@LottoActivity, 10)

        dialog = Dialog(this@LottoActivity)
        dialog.setContentView(dialogBinding.root)
        dialog.setCancelable(false)
        val window = dialog.window
        window!!.setLayout(
            GridLayout.LayoutParams.MATCH_PARENT,
            GridLayout.LayoutParams.WRAP_CONTENT
        )

        when (view!!.id) {
            R.id.ll_max_no -> {
                dialog.show()
                val adapter = MyNumbersAdapter(listOfFirst5Cols, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@LottoActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                binding.tvMaxNo.text = listOfFirst5Cols[position]
                                dialog.dismiss()
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )

            }

            R.id.ll_col_no -> {
                dialog.show()
                val adapter = MyNumbersAdapter(listOfColumns, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@LottoActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                binding.tvColNo.text = listOfColumns[position]
                                binding.recyclerview6column.visibility = View.GONE
                                if (binding.tvColNo.text.toString() == "5") {
                                    layoutSix!!.visibility = View.GONE
                                    layoutSeven!!.visibility = View.GONE
                                    dynamicCol.visibility = VISIBLE
                                    layoutFive!!.visibility = VISIBLE
                                    selectedNumber = ArrayList<String>()
                                    sixNumberList.clear()
                                    sevenNumberList.clear()
                                }
                                if (binding.tvColNo.text.toString() == "6") {
                                    layoutFive!!.visibility = View.GONE
                                    layoutSeven!!.visibility = View.GONE
                                    dynamicCol.visibility = VISIBLE
                                    layoutSix!!.visibility = VISIBLE
                                    selectedNumber = ArrayList<String>()
                                    fiveNumberList.clear()
                                    sevenNumberList.clear()
                                }
                                if (binding.tvColNo.text.toString() == "7") {
                                    layoutSix!!.visibility = View.GONE
                                    layoutFive!!.visibility = View.GONE
                                    dynamicCol.visibility = VISIBLE
                                    layoutSeven!!.visibility = VISIBLE
                                    selectedNumber = ArrayList<String>()
                                    sixNumberList.clear()
                                    fiveNumberList.clear()
                                }

                                dialog.dismiss()
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )
            }

            R.id.ll_tv1_5 -> {
                if (binding.tvMaxNo.text == "-") {
                    customToast("First, Select a number from first 5 box.")
                    return
                }
                dialog.show()

                var list = ArrayList<String>()
                for (i in 1..Integer.parseInt(binding.tvMaxNo.text.toString())) {
                    list.add(i.toString())
                }
                val adapter = MyNumbersAdapter(list, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@LottoActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                if (selectedNumber.isNotEmpty() && selectedNumber.contains(list[position])) {
                                    customToast("Already selected, please select another number.")
                                } else {
                                    layoutColFiveBinding.tv15.text = list[position]
                                    selectedNumber.add(list[position])
                                    dialog.dismiss()
                                }
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )

            }

            R.id.ll_tv2_5 -> {
                if (binding.tvMaxNo.text == "-") {
                    customToast("First, Select a number from first 5 box.")
                    return
                }
                dialog.show()

                var list = ArrayList<String>()
                for (i in 1..Integer.parseInt(binding.tvMaxNo.text.toString())) {
                    list.add(i.toString())
                }
                val adapter = MyNumbersAdapter(list, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@LottoActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                if (selectedNumber.isNotEmpty() && selectedNumber.contains(list[position])) {
                                    customToast("Already selected, please select another number.")
                                } else {
                                    layoutColFiveBinding.tv25.text = list[position]
                                    selectedNumber.add(list[position])
                                    dialog.dismiss()
                                }
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )

            }

            R.id.ll_tv3_5 -> {
                if (binding.tvMaxNo.text == "-") {
                    customToast("First, Select a number from first 5 box.")
                    return
                }
                dialog.show()

                var list = ArrayList<String>()
                for (i in 1..Integer.parseInt(binding.tvMaxNo.text.toString())) {
                    list.add(i.toString())
                }
                val adapter = MyNumbersAdapter(list, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@LottoActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                if (selectedNumber.isNotEmpty() && selectedNumber.contains(list[position])) {
                                    customToast("Already selected, please select another number.")
                                } else {
                                    layoutColFiveBinding.tv35.text = list[position]
                                    selectedNumber.add(list[position])
                                    dialog.dismiss()
                                }
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )

            }

            R.id.ll_tv4_5 -> {
                if (binding.tvMaxNo.text == "-") {
                    customToast("First, Select a number from first 5 box.")
                    return
                }
                dialog.show()

                var list = ArrayList<String>()
                for (i in 1..Integer.parseInt(binding.tvMaxNo.text.toString())) {
                    list.add(i.toString())
                }
                val adapter = MyNumbersAdapter(list, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@LottoActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                if (selectedNumber.isNotEmpty() && selectedNumber.contains(list[position])) {
                                    customToast("Already selected, please select another number.")
                                } else {
                                    layoutColFiveBinding.tv45.text = list[position]
                                    selectedNumber.add(list[position])
                                    dialog.dismiss()
                                }
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )

            }

            R.id.ll_tv5_5 -> {
                if (binding.tvMaxNo.text == "-") {
                    customToast("First, Select a number from first 5 box.")
                    return
                }
                dialog.show()

                var list = ArrayList<String>()
                for (i in 1..Integer.parseInt(binding.tvMaxNo.text.toString())) {
                    list.add(i.toString())
                }
                val adapter = MyNumbersAdapter(list, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@LottoActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                if (selectedNumber.isNotEmpty() && selectedNumber.contains(list[position])) {
                                    customToast("Already selected, please select another number.")
                                } else {
                                    layoutColFiveBinding.tv55.text = list[position]
                                    selectedNumber.add(list[position])
                                    selectedNumber.clear()
                                    dialog.dismiss()
                                }
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )

            }

            R.id.ll_tv6_5 -> {
                if (binding.tvMaxNo.text == "-") {
                    customToast("First, Select a number from first 6 box.")
                    return
                }
//                else if (binding.tv1.text.toString() == "-" || binding.tv2.text.toString() == "-" || binding.tv3.text.toString() == "-"
//                    || binding.tv4.text.toString() == "-" || binding.tv5.text.toString() == "-"
//                ) {
//                    customToast("Please select previous box/boxes of this row.")
//                    return
//                }
                dialog.show()

                var list = ArrayList<String>()
                for (i in 1..Integer.parseInt(binding.tvMaxNo.text.toString())) {
                    list.add(i.toString())
                }
                val adapter = MyNumbersAdapter(list, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@LottoActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                if (selectedNumber.isNotEmpty() && selectedNumber.contains(list[position])) {
                                    customToast("Already selected, please select another number.")
                                } else {
                                    layoutColFiveBinding.tv65.text = list[position]
                                    selectedNumber.clear()
                                    dialog.dismiss()
                                }
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )

            }

            R.id.ll_tv7_5 -> {
                if (binding.tvMaxNo.text == "-") {
                    customToast("First, Select a number from first 5 box.")
                    return
                }
                dialog.show()

                var list = ArrayList<String>()
                for (i in 1..Integer.parseInt(binding.tvMaxNo.text.toString())) {
                    list.add(i.toString())
                }
                val adapter = MyNumbersAdapter(list, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@LottoActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                if (selectedNumber.isNotEmpty() && selectedNumber.contains(list[position])) {
                                    customToast("Already selected, please select another number.")
                                }
                                layoutColFiveBinding.tv75.text = list[position]
                                selectedNumber.add(list[position])
                                dialog.dismiss()
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )

            }

            R.id.ll_tv8_5 -> {
                if (binding.tvMaxNo.text == "-") {
                    customToast("First, Select a number from first 5 box.")
                    return
                }
                dialog.show()

                var list = ArrayList<String>()
                for (i in 1..Integer.parseInt(binding.tvMaxNo.text.toString())) {
                    list.add(i.toString())
                }
                val adapter = MyNumbersAdapter(list, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@LottoActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                if (selectedNumber.isNotEmpty() && selectedNumber.contains(list[position])) {
                                    customToast("Already selected, please select another number.")
                                } else {
                                    layoutColFiveBinding.tv85.text = list[position]
                                    selectedNumber.add(list[position])
                                    dialog.dismiss()
                                }
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )

            }

            R.id.ll_tv9_5 -> {
                if (binding.tvMaxNo.text == "-") {
                    customToast("First, Select a number from first 5 box.")
                    return
                }
                dialog.show()

                var list = ArrayList<String>()
                for (i in 1..Integer.parseInt(binding.tvMaxNo.text.toString())) {
                    list.add(i.toString())
                }
                val adapter = MyNumbersAdapter(list, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@LottoActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                if (selectedNumber.isNotEmpty() && selectedNumber.contains(list[position])) {
                                    customToast("Already selected, please select another number.")
                                } else {
                                    layoutColFiveBinding.tv95.text = list[position]
                                    selectedNumber.add(list[position])
                                    dialog.dismiss()
                                }
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )

            }

            R.id.ll_tv10_5 -> {
                if (binding.tvMaxNo.text == "-") {
                    customToast("First, Select a number from first 5 box.")
                    return
                }
                dialog.show()

                var list = ArrayList<String>()
                for (i in 1..Integer.parseInt(binding.tvMaxNo.text.toString())) {
                    list.add(i.toString())
                }
                val adapter = MyNumbersAdapter(list, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@LottoActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                if (selectedNumber.isNotEmpty() && selectedNumber.contains(list[position])) {
                                    customToast("Already selected, please select another number.")
                                } else {
                                    layoutColFiveBinding.tv105.text = list[position]
                                    selectedNumber.add(list[position])
                                    dialog.dismiss()
                                }
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )

            }

            R.id.ll_tv1_6 -> {
                if (binding.tvMaxNo.text == "-") {
                    customToast("First, Select a number from first 5 box.")
                    return
                }
                dialog.show()

                var list = ArrayList<String>()
                for (i in 1..Integer.parseInt(binding.tvMaxNo.text.toString())) {
                    list.add(i.toString())
                }
                val adapter = MyNumbersAdapter(list, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@LottoActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                if (selectedNumber.isNotEmpty() && selectedNumber.contains(list[position])) {
                                    customToast("Already selected, please select another number.")
                                } else {
                                    layoutColSixBinding.tv16.text = list[position]
                                    selectedNumber.add(list[position])
                                    dialog.dismiss()
                                }
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )

            }

            R.id.ll_tv2_6 -> {
                if (binding.tvMaxNo.text == "-") {
                    customToast("First, Select a number from first 5 box.")
                    return
                }
                dialog.show()

                var list = ArrayList<String>()
                for (i in 1..Integer.parseInt(binding.tvMaxNo.text.toString())) {
                    list.add(i.toString())
                }
                val adapter = MyNumbersAdapter(list, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@LottoActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                if (selectedNumber.isNotEmpty() && selectedNumber.contains(list[position])) {
                                    customToast("Already selected, please select another number.")
                                } else {
                                    layoutColSixBinding.tv26.text = list[position]
                                    selectedNumber.add(list[position])
                                    dialog.dismiss()
                                }
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )
            }

            R.id.ll_tv3_6 -> {
                if (binding.tvMaxNo.text == "-") {
                    customToast("First, Select a number from first 5 box.")
                    return
                }
                dialog.show()

                var list = ArrayList<String>()
                for (i in 1..Integer.parseInt(binding.tvMaxNo.text.toString())) {
                    list.add(i.toString())
                }
                val adapter = MyNumbersAdapter(list, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@LottoActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                if (selectedNumber.isNotEmpty() && selectedNumber.contains(list[position])) {
                                    customToast("Already selected, please select another number.")
                                } else {
                                    layoutColSixBinding.tv36.text = list[position]
                                    selectedNumber.add(list[position])
                                    dialog.dismiss()
                                }
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )
            }

            R.id.ll_tv4_6 -> {
                if (binding.tvMaxNo.text == "-") {
                    customToast("First, Select a number from first 5 box.")
                    return
                }
                dialog.show()

                var list = ArrayList<String>()
                for (i in 1..Integer.parseInt(binding.tvMaxNo.text.toString())) {
                    list.add(i.toString())
                }
                val adapter = MyNumbersAdapter(list, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@LottoActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                if (selectedNumber.isNotEmpty() && selectedNumber.contains(list[position])) {
                                    customToast("Already selected, please select another number.")
                                } else {
                                    layoutColSixBinding.tv46.text = list[position]
                                    selectedNumber.add(list[position])
                                    dialog.dismiss()
                                }
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )
            }

            R.id.ll_tv5_6 -> {
                if (binding.tvMaxNo.text == "-") {
                    customToast("First, Select a number from first 5 box.")
                    return
                }
                dialog.show()

                var list = ArrayList<String>()
                for (i in 1..Integer.parseInt(binding.tvMaxNo.text.toString())) {
                    list.add(i.toString())
                }
                val adapter = MyNumbersAdapter(list, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@LottoActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                if (selectedNumber.isNotEmpty() && selectedNumber.contains(list[position])) {
                                    customToast("Already selected, please select another number.")
                                } else {
                                    layoutColSixBinding.tv56.text = list[position]
                                    selectedNumber.add(list[position])
                                    dialog.dismiss()
                                }
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )
            }

            R.id.ll_tv6_6 -> {
                if (binding.tvMaxNo.text == "-") {
                    customToast("First, Select a number from first 5 box.")
                    return
                }
                dialog.show()

                var list = ArrayList<String>()
                for (i in 1..Integer.parseInt(binding.tvMaxNo.text.toString())) {
                    list.add(i.toString())
                }
                val adapter = MyNumbersAdapter(list, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@LottoActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                if (selectedNumber.isNotEmpty() && selectedNumber.contains(list[position])) {
                                    customToast("Already selected, please select another number.")
                                } else {
                                    layoutColSixBinding.tv66.text = list[position]
                                    selectedNumber.add(list[position])
                                    selectedNumber.clear()
                                    dialog.dismiss()
                                }
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )
            }

            R.id.ll_tv7_6 -> {
                if (binding.tvMaxNo.text == "-") {
                    customToast("First, Select a number from first 5 box.")
                    return
                }
                dialog.show()

                var list = ArrayList<String>()
                for (i in 1..Integer.parseInt(binding.tvMaxNo.text.toString())) {
                    list.add(i.toString())
                }
                val adapter = MyNumbersAdapter(list, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@LottoActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                if (selectedNumber.isNotEmpty() && selectedNumber.contains(list[position])) {
                                    customToast("Already selected, please select another number.")
                                } else {
                                    layoutColSixBinding.tv76.text = list[position]
                                    selectedNumber.add(list[position])
                                    dialog.dismiss()
                                }
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )
            }

            R.id.ll_tv7_6 -> {
                if (binding.tvMaxNo.text == "-") {
                    customToast("First, Select a number from first 5 box.")
                    return
                }
                dialog.show()

                var list = ArrayList<String>()
                for (i in 1..Integer.parseInt(binding.tvMaxNo.text.toString())) {
                    list.add(i.toString())
                }
                val adapter = MyNumbersAdapter(list, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@LottoActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                if (selectedNumber.isNotEmpty() && selectedNumber.contains(list[position])) {
                                    customToast("Already selected, please select another number.")
                                } else {
                                    layoutColSixBinding.tv76.text = list[position]
                                    selectedNumber.add(list[position])
                                    dialog.dismiss()
                                }
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )
            }

            R.id.ll_tv8_6 -> {
                if (binding.tvMaxNo.text == "-") {
                    customToast("First, Select a number from first 5 box.")
                    return
                }
                dialog.show()

                var list = ArrayList<String>()
                for (i in 1..Integer.parseInt(binding.tvMaxNo.text.toString())) {
                    list.add(i.toString())
                }
                val adapter = MyNumbersAdapter(list, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@LottoActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                if (selectedNumber.isNotEmpty() && selectedNumber.contains(list[position])) {
                                    customToast("Already selected, please select another number.")
                                } else {
                                    layoutColSixBinding.tv86.text = list[position]
                                    selectedNumber.add(list[position])
                                    dialog.dismiss()
                                }
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )
            }

            R.id.ll_tv9_6 -> {
                if (binding.tvMaxNo.text == "-") {
                    customToast("First, Select a number from first 5 box.")
                    return
                }
                dialog.show()

                var list = ArrayList<String>()
                for (i in 1..Integer.parseInt(binding.tvMaxNo.text.toString())) {
                    list.add(i.toString())
                }
                val adapter = MyNumbersAdapter(list, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@LottoActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                if (selectedNumber.isNotEmpty() && selectedNumber.contains(list[position])) {
                                    customToast("Already selected, please select another number.")
                                } else {
                                    layoutColSixBinding.tv96.text = list[position]
                                    selectedNumber.add(list[position])
                                    dialog.dismiss()
                                }
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )
            }

            R.id.ll_tv10_6 -> {
                if (binding.tvMaxNo.text == "-") {
                    customToast("First, Select a number from first 5 box.")
                    return
                }
                dialog.show()

                var list = ArrayList<String>()
                for (i in 1..Integer.parseInt(binding.tvMaxNo.text.toString())) {
                    list.add(i.toString())
                }
                val adapter = MyNumbersAdapter(list, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@LottoActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                if (selectedNumber.isNotEmpty() && selectedNumber.contains(list[position])) {
                                    customToast("Already selected, please select another number.")
                                } else {
                                    layoutColSixBinding.tv106.text = list[position]
                                    selectedNumber.add(list[position])
                                    dialog.dismiss()
                                }
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )
            }

            R.id.ll_tv11_6 -> {
                if (binding.tvMaxNo.text == "-") {
                    customToast("First, Select a number from first 5 box.")
                    return
                }
                dialog.show()

                var list = ArrayList<String>()
                for (i in 1..Integer.parseInt(binding.tvMaxNo.text.toString())) {
                    list.add(i.toString())
                }
                val adapter = MyNumbersAdapter(list, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@LottoActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                if (selectedNumber.isNotEmpty() && selectedNumber.contains(list[position])) {
                                    customToast("Already selected, please select another number.")
                                } else {
                                    layoutColSixBinding.tv116.text = list[position]
                                    selectedNumber.add(list[position])
                                    dialog.dismiss()
                                }
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )
            }

            R.id.ll_tv12_6 -> {
                if (binding.tvMaxNo.text == "-") {
                    customToast("First, Select a number from first 5 box.")
                    return
                }
                dialog.show()

                var list = ArrayList<String>()
                for (i in 1..Integer.parseInt(binding.tvMaxNo.text.toString())) {
                    list.add(i.toString())
                }
                val adapter = MyNumbersAdapter(list, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@LottoActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                if (selectedNumber.isNotEmpty() && selectedNumber.contains(list[position])) {
                                    customToast("Already selected, please select another number.")
                                } else {
                                    layoutColSixBinding.tv126.text = list[position]
                                    selectedNumber.add(list[position])
                                    dialog.dismiss()
                                }
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )
            }

            R.id.ll_tv1_7 -> {
                if (binding.tvMaxNo.text == "-") {
                    customToast("First, Select a number from first 5 box.")
                    return
                }
                dialog.show()

                var list = ArrayList<String>()
                for (i in 1..Integer.parseInt(binding.tvMaxNo.text.toString())) {
                    list.add(i.toString())
                }
                val adapter = MyNumbersAdapter(list, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@LottoActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                if (selectedNumber.isNotEmpty() && selectedNumber.contains(list[position])) {
                                    customToast("Already selected, please select another number.")
                                } else {
                                    layoutColSevenBinding.tv17.text = list[position]
                                    selectedNumber.add(list[position])
                                    dialog.dismiss()
                                }
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )
            }

            R.id.ll_tv2_7 -> {
                if (binding.tvMaxNo.text == "-") {
                    customToast("First, Select a number from first 5 box.")
                    return
                }
                dialog.show()

                var list = ArrayList<String>()
                for (i in 1..Integer.parseInt(binding.tvMaxNo.text.toString())) {
                    list.add(i.toString())
                }
                val adapter = MyNumbersAdapter(list, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@LottoActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                if (selectedNumber.isNotEmpty() && selectedNumber.contains(list[position])) {
                                    customToast("Already selected, please select another number.")
                                } else {
                                    layoutColSevenBinding.tv27.text = list[position]
                                    selectedNumber.add(list[position])
                                    dialog.dismiss()
                                }
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )
            }

            R.id.ll_tv3_7 -> {
                if (binding.tvMaxNo.text == "-") {
                    customToast("First, Select a number from first 5 box.")
                    return
                }
                dialog.show()

                var list = ArrayList<String>()
                for (i in 1..Integer.parseInt(binding.tvMaxNo.text.toString())) {
                    list.add(i.toString())
                }
                val adapter = MyNumbersAdapter(list, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@LottoActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                if (selectedNumber.isNotEmpty() && selectedNumber.contains(list[position])) {
                                    customToast("Already selected, please select another number.")
                                } else {
                                    layoutColSevenBinding.tv37.text = list[position]
                                    selectedNumber.add(list[position])
                                    dialog.dismiss()
                                }
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )
            }

            R.id.ll_tv4_7 -> {
                if (binding.tvMaxNo.text == "-") {
                    customToast("First, Select a number from first 5 box.")
                    return
                }
                dialog.show()

                var list = ArrayList<String>()
                for (i in 1..Integer.parseInt(binding.tvMaxNo.text.toString())) {
                    list.add(i.toString())
                }
                val adapter = MyNumbersAdapter(list, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@LottoActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                if (selectedNumber.isNotEmpty() && selectedNumber.contains(list[position])) {
                                    customToast("Already selected, please select another number.")
                                } else {
                                    layoutColSevenBinding.tv47.text = list[position]
                                    selectedNumber.add(list[position])
                                    dialog.dismiss()
                                }
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )
            }

            R.id.ll_tv5_7 -> {
                if (binding.tvMaxNo.text == "-") {
                    customToast("First, Select a number from first 5 box.")
                    return
                }
                dialog.show()

                var list = ArrayList<String>()
                for (i in 1..Integer.parseInt(binding.tvMaxNo.text.toString())) {
                    list.add(i.toString())
                }
                val adapter = MyNumbersAdapter(list, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@LottoActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                if (selectedNumber.isNotEmpty() && selectedNumber.contains(list[position])) {
                                    customToast("Already selected, please select another number.")
                                } else {
                                    layoutColSevenBinding.tv57.text = list[position]
                                    selectedNumber.add(list[position])
                                    dialog.dismiss()
                                }
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )
            }

            R.id.ll_tv6_7 -> {
                if (binding.tvMaxNo.text == "-") {
                    customToast("First, Select a number from first 5 box.")
                    return
                }
                dialog.show()

                var list = ArrayList<String>()
                for (i in 1..Integer.parseInt(binding.tvMaxNo.text.toString())) {
                    list.add(i.toString())
                }
                val adapter = MyNumbersAdapter(list, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@LottoActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                if (selectedNumber.isNotEmpty() && selectedNumber.contains(list[position])) {
                                    customToast("Already selected, please select another number.")
                                } else {
                                    layoutColSevenBinding.tv67.text = list[position]
                                    selectedNumber.add(list[position])
                                    dialog.dismiss()
                                }
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )
            }

            R.id.ll_tv7_7 -> {
                if (binding.tvMaxNo.text == "-") {
                    customToast("First, Select a number from first 5 box.")
                    return
                }
                dialog.show()

                var list = ArrayList<String>()
                for (i in 1..Integer.parseInt(binding.tvMaxNo.text.toString())) {
                    list.add(i.toString())
                }
                val adapter = MyNumbersAdapter(list, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@LottoActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                if (selectedNumber.isNotEmpty() && selectedNumber.contains(list[position])) {
                                    customToast("Already selected, please select another number.")
                                } else {
                                    layoutColSevenBinding.tv77.text = list[position]
                                    selectedNumber.add(list[position])
                                    selectedNumber.clear()
                                    dialog.dismiss()
                                }
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )
            }

            R.id.ll_tv8_7 -> {
                if (binding.tvMaxNo.text == "-") {
                    customToast("First, Select a number from first 5 box.")
                    return
                }
                dialog.show()

                var list = ArrayList<String>()
                for (i in 1..Integer.parseInt(binding.tvMaxNo.text.toString())) {
                    list.add(i.toString())
                }
                val adapter = MyNumbersAdapter(list, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@LottoActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                if (selectedNumber.isNotEmpty() && selectedNumber.contains(list[position])) {
                                    customToast("Already selected, please select another number.")
                                } else {
                                    layoutColSevenBinding.tv87.text = list[position]
                                    selectedNumber.add(list[position])
                                    dialog.dismiss()
                                }
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )
            }

            R.id.ll_tv9_7 -> {
                if (binding.tvMaxNo.text == "-") {
                    customToast("First, Select a number from first 5 box.")
                    return
                }
                dialog.show()

                var list = ArrayList<String>()
                for (i in 1..Integer.parseInt(binding.tvMaxNo.text.toString())) {
                    list.add(i.toString())
                }
                val adapter = MyNumbersAdapter(list, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@LottoActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                if (selectedNumber.isNotEmpty() && selectedNumber.contains(list[position])) {
                                    customToast("Already selected, please select another number.")
                                } else {
                                    layoutColSevenBinding.tv97.text = list[position]
                                    selectedNumber.add(list[position])
                                    dialog.dismiss()
                                }
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )
            }

            R.id.ll_tv10_7 -> {
                if (binding.tvMaxNo.text == "-") {
                    customToast("First, Select a number from first 5 box.")
                    return
                }
                dialog.show()

                var list = ArrayList<String>()
                for (i in 1..Integer.parseInt(binding.tvMaxNo.text.toString())) {
                    list.add(i.toString())
                }
                val adapter = MyNumbersAdapter(list, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@LottoActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                if (selectedNumber.isNotEmpty() && selectedNumber.contains(list[position])) {
                                    customToast("Already selected, please select another number.")
                                } else {
                                    layoutColSevenBinding.tv107.text = list[position]
                                    selectedNumber.add(list[position])
                                    dialog.dismiss()
                                }
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )
            }

            R.id.ll_tv11_7 -> {
                if (binding.tvMaxNo.text == "-") {
                    customToast("First, Select a number from first 5 box.")
                    return
                }
                dialog.show()

                var list = ArrayList<String>()
                for (i in 1..Integer.parseInt(binding.tvMaxNo.text.toString())) {
                    list.add(i.toString())
                }
                val adapter = MyNumbersAdapter(list, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@LottoActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                if (selectedNumber.isNotEmpty() && selectedNumber.contains(list[position])) {
                                    customToast("Already selected, please select another number.")
                                } else {
                                    layoutColSevenBinding.tv117.text = list[position]
                                    selectedNumber.add(list[position])
                                    dialog.dismiss()
                                }
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )
            }

            R.id.ll_tv12_7 -> {
                if (binding.tvMaxNo.text == "-") {
                    customToast("First, Select a number from first 5 box.")
                    return
                }
                dialog.show()

                var list = ArrayList<String>()
                for (i in 1..Integer.parseInt(binding.tvMaxNo.text.toString())) {
                    list.add(i.toString())
                }
                val adapter = MyNumbersAdapter(list, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@LottoActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                if (selectedNumber.isNotEmpty() && selectedNumber.contains(list[position])) {
                                    customToast("Already selected, please select another number.")
                                } else {
                                    layoutColSevenBinding.tv127.text = list[position]
                                    selectedNumber.add(list[position])
                                    dialog.dismiss()
                                }
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )
            }

            R.id.ll_tv13_7 -> {
                if (binding.tvMaxNo.text == "-") {
                    customToast("First, Select a number from first 5 box.")
                    return
                }
                dialog.show()

                var list = ArrayList<String>()
                for (i in 1..Integer.parseInt(binding.tvMaxNo.text.toString())) {
                    list.add(i.toString())
                }
                val adapter = MyNumbersAdapter(list, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@LottoActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                if (selectedNumber.isNotEmpty() && selectedNumber.contains(list[position])) {
                                    customToast("Already selected, please select another number.")
                                } else {
                                    layoutColSevenBinding.tv137.text = list[position]
                                    selectedNumber.add(list[position])
                                    dialog.dismiss()
                                }
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )
            }

            R.id.ll_tv14_7 -> {
                if (binding.tvMaxNo.text == "-") {
                    customToast("First, Select a number from first 5 box.")
                    return
                }
                dialog.show()

                var list = ArrayList<String>()
                for (i in 1..Integer.parseInt(binding.tvMaxNo.text.toString())) {
                    list.add(i.toString())
                }
                val adapter = MyNumbersAdapter(list, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@LottoActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                if (selectedNumber.isNotEmpty() && selectedNumber.contains(list[position])) {
                                    customToast("Already selected, please select another number.")
                                } else {
                                    layoutColSevenBinding.tv147.text = list[position]
                                    selectedNumber.add(list[position])
                                    dialog.dismiss()
                                }
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )
            }

            R.id.btn_gen_2nos -> {
                binding.recyclerview6column.visibility = View.VISIBLE
                var adRequest = AdRequest.Builder().build()
                InterstitialAd.load(
                    this,
                    "ca-app-pub-3164749634609559/7102887092",
                    adRequest,
                    object : InterstitialAdLoadCallback() {
                        override fun onAdFailedToLoad(adError: LoadAdError) {
                            adError?.toString()?.let { Log.d(TAG, it) }
                            mInterstitialAd = null
                        }

                        override fun onAdLoaded(interstitialAd: InterstitialAd) {
                            Log.d(TAG, "Ad was loaded.")
                            mInterstitialAd = interstitialAd
                        }
                    })

                if (mInterstitialAd != null) {
//                    mInterstitialAd?.show(this)
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.")
                }

                if (binding.tvColNo.text.toString() != "-" && binding.tvColNo.text.toString() == "6") {
                    if (binding.tvMaxNo.text.toString() == "-"
                        || layoutColSixBinding.tv16.text.toString() == "-" || layoutColSixBinding.tv26.text.toString() == "-" || layoutColSixBinding.tv36.text.toString() == "-"
                        || layoutColSixBinding.tv46.text.toString() == "-" || layoutColSixBinding.tv56.text.toString() == "-" || layoutColSixBinding.tv66.text.toString() == "-"
                        || layoutColSixBinding.tv76.text.toString() == "-" || layoutColSixBinding.tv86.text.toString() == "-" || layoutColSixBinding.tv96.text.toString() == "-"
                        || layoutColSixBinding.tv106.text.toString() == "-" || layoutColSixBinding.tv116.text.toString() == "-" || layoutColSixBinding.tv126.text.toString() == "-"
                    ) {
                        customToast("Result can't predict, please select number by clicking box.")
                        return
                    } else {
                        var string1: String? = null
                        var string2: String? = null
                        var string3: String? = null
                        var string4: String? = null
                        var string5: String? = null
                        var string6: String? = null
                        var string7: String? = null
                        var string8: String? = null
                        var string9: String? = null
                        var string10: String? = null
                        var string11: String? = null
                        var string12: String? = null

                        if (layoutColSixBinding.tv16.text.toString().toInt() in 1..9) {
                            string1 = "11"
                        } else {
                            string1 = layoutColSixBinding.tv16.text.toString()
                        }
                        if (layoutColSixBinding.tv26.text.toString().toInt() in 1..9) {
                            string2 = "13"
                        } else {
                            string2 = layoutColSixBinding.tv26.text.toString()
                        }
                        if (layoutColSixBinding.tv36.text.toString().toInt() in 1..9) {
                            string3 = "15"
                        } else {
                            string3 = layoutColSixBinding.tv36.text.toString()
                        }
                        if (layoutColSixBinding.tv46.text.toString().toInt() in 1..9) {
                            string4 = "17"
                        } else {
                            string4 = layoutColSixBinding.tv46.text.toString()
                        }
                        if (layoutColSixBinding.tv56.text.toString().toInt() in 1..9) {
                            string5 = "19"
                        } else {
                            string5 = layoutColSixBinding.tv56.text.toString()
                        }
                        if (layoutColSixBinding.tv66.text.toString().toInt() in 1..9) {
                            string6 = "21"
                        } else {
                            string6 = layoutColSixBinding.tv66.text.toString()
                        }
                        if (layoutColSixBinding.tv76.text.toString().toInt() in 1..9) {
                            string7 = "23"
                        } else {
                            string7 = layoutColSixBinding.tv76.text.toString()
                        }
                        if (layoutColSixBinding.tv86.text.toString().toInt() in 1..9) {
                            string8 = "25"
                        } else {
                            string8 = layoutColSixBinding.tv86.text.toString()
                        }
                        if (layoutColSixBinding.tv96.text.toString().toInt() in 1..9) {
                            string9 = "27"
                        } else {
                            string9 = layoutColSixBinding.tv96.text.toString()
                        }
                        if (layoutColSixBinding.tv106.text.toString().toInt() in 1..9) {
                            string10 = "29"
                        } else {
                            string10 = layoutColSixBinding.tv106.text.toString()
                        }
                        if (layoutColSixBinding.tv116.text.toString().toInt() in 1..9) {
                            string11 = "31"
                        } else {
                            string11 = layoutColSixBinding.tv116.text.toString()
                        }
                        if (layoutColSixBinding.tv126.text.toString().toInt() in 1..9) {
                            string12 = "33"
                        } else {
                            string12 = layoutColSixBinding.tv126.text.toString()
                        }
                        sumCol17 = Integer.parseInt(string1).plus(Integer.parseInt(string7))
                        sumCol28 = Integer.parseInt(string2).plus(Integer.parseInt(string8))
                        sumCol39 = Integer.parseInt(string3).plus(Integer.parseInt(string9))
                        sumCol410 = Integer.parseInt(string4).plus(Integer.parseInt(string10))
                        sumCol511 = Integer.parseInt(string5).plus(Integer.parseInt(string11))
                        sumCol612 = Integer.parseInt(string6).plus(Integer.parseInt(string12))

                        var sumDigits17: Int = getSumOfDigits(sumCol17!!)
                        var sumDigits28: Int = getSumOfDigits(sumCol28!!)
                        var sumDigits39: Int = getSumOfDigits(sumCol39!!)
                        var sumDigits410: Int = getSumOfDigits(sumCol410!!)
                        var sumDigits511: Int = getSumOfDigits(sumCol511!!)
                        var sumDigits612: Int = getSumOfDigits(sumCol612!!)

                        val powSumDigits17 =
                            BigInteger(sumDigits17.toString()).pow(80)
                        val powSumDigits28 =
                            BigInteger(sumDigits28.toString()).pow(80)
                        val powSumDigits39 =
                            BigInteger(sumDigits39.toString()).pow(80)
                        val powSumDigits410 =
                            BigInteger(sumDigits410.toString()).pow(80)
                        val powSumDigits511 =
                            BigInteger(sumDigits511.toString()).pow(80)
                        val powSumDigits612 =
                            BigInteger(sumDigits612.toString()).pow(80)

                        val result17 =
                            powSumDigits17.toString().replace("0", "7").map { it.toString() }.toTypedArray()
                        val result28 =
                            powSumDigits28.toString().replace("0", "8").map { it.toString() }.toTypedArray()
                        val result39 =
                            powSumDigits39.toString().replace("0", "9").map { it.toString() }.toTypedArray()
                        val result410 =
                            powSumDigits410.toString().replace("0", "1").map { it.toString() }.toTypedArray()
                        val result511 =
                            powSumDigits511.toString().replace("0", "2").map { it.toString() }.toTypedArray()
                        val result612 =
                            powSumDigits612.toString().replace("0", "3").map { it.toString() }.toTypedArray()

                        var num1: String? = null
                        var num2: String? = null
                        var num3: String? = null
                        var num4: String? = null
                        var num5: String? = null
                        var num6: String? = null

                        sixNumberList.clear()
                        for (i in 1..2) {
                            if (i == 1) {
                                num1 = result17[25] + result17[26]
                                num2 = result28[26] + result28[33]
                                num3 = result39[33] + result39[34]
                                num4 = result410[34] + result410[35]
                                num5 = result511[35] + result511[26]
                                num6 = result612[26] + result612[25]
                                if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                                } else {
                                    num1 = num1.toInt().div(2).plus(1).toString()
                                    if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                                        num1 = num1.toInt().div(4).plus(1).toString()
                                    }

                                }
                                if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                                } else {
                                    num2 = num2.toInt().div(2).toString()
                                    if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                                        num2 = num2.toInt().div(4).plus(1).toString()
                                    }
                                }
                                if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                                } else {
                                    num3 = num3.toInt().div(2)
                                        .toString()
                                    if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                                        num3 = num3.toInt().div(4).plus(1).toString()
                                    }
                                }
                                if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                                } else {
                                    num4 = num4.toInt().div(2)
                                        .toString()
                                    if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                                        num4 = num4.toInt().div(4).plus(1).toString()
                                    }
                                }
                                if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                                } else {
                                    num5 = binding.tvMaxNo.text.toString().toInt()!!.minus(1)
                                        .toString()
                                    if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                                        num5 = num5.toInt().div(4).plus(1).toString()
                                    }
                                }
                                if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                                } else {
                                    num6 = num6.toInt().div(2).toString()
                                    if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                                        num6 = num6.toInt().div(4).plus(1).toString()
                                    }
                                }
                                if (num1.toInt() == num2.toInt()) num2 =
                                    num2.toInt().plus(4).toString()
                                if (num1.toInt() == num3.toInt()) num3 =
                                    num3.toInt().plus(1).toString()
                                if (num1.toInt() == num4.toInt()) num4 =
                                    num4.toInt().div(2).toString()
                                if (num1.toInt() == num5.toInt()) num5 =
                                    num5.toInt().plus(3).toString()
                                if (num1.toInt() == num6.toInt()) num6 =
                                    num6.toInt().plus(4).toString()

                                if (num2.toInt() == num3.toInt()) num3 =
                                    num3.toInt().plus(5).toString()
                                if (num2.toInt() == num4.toInt()) num4 =
                                    num4.toInt().plus(6).toString()
                                if (num2.toInt() == num5.toInt()) num5 =
                                    num5.toInt().plus(7).toString()
                                if (num2.toInt() == num6.toInt()) num6 =
                                    num6.toInt().plus(8).toString()

                                if (num3.toInt() == num4.toInt()) num4 =
                                    num4.toInt().plus(1).toString()
                                if (num3.toInt() == num5.toInt()) num5 =
                                    num5.toInt().div(2).toString()
                                if (num3.toInt() == num6.toInt()) num6 =
                                    num2.toInt().plus(3).toString()

                                if (num4.toInt() == num5.toInt()) num5 =
                                    num5.toInt().plus(4).toString()
                                if (num4.toInt() == num6.toInt()) num6 =
                                    num6.toInt().plus(5).toString()

                                if (num5.toInt() == num6.toInt()) num6 =
                                    num6.toInt().plus(6).toString()

                                var sixNumbers = SixNumber(num1, num2, num3, num4, num5, num6)
                                sixNumberList.add(sixNumbers)
                            }
                            if (i == 2) {
                                num1 = result28[8] + result28[9]
                                num2 = result28[8] + result28[9]
                                num3 = result39[9] + result39[33]
                                num4 = result410[33] + result410[11]
                                num5 = result511[11] + result612[12]
                                num6 = result612[12] + result511[27]
                                if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                                } else {
                                    num1 = num1.toInt().div(2).plus(1).toString()
                                    if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                                        num1 = num1.toInt().div(4).plus(1).toString()
                                    }

                                }
                                if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                                } else {
                                    num2 = num2.toInt().div(2).toString()
                                    if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                                        num2 = num2.toInt().div(4).plus(1).toString()
                                    }
                                }
                                if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                                } else {
                                    num3 = num3.toInt().div(2)
                                        .toString()
                                    if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                                        num3 = num3.toInt().div(4).plus(1).toString()
                                    }
                                }
                                if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                                } else {
                                    num4 = binding.tvMaxNo.text.toString().toInt()!!.minus(3)
                                        .toString()
                                    if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                                        num4 = num4.toInt().div(4).plus(1).toString()
                                    }
                                }
                                if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                                } else {
                                    num5 = binding.tvMaxNo.text.toString().toInt()!!.minus(2)
                                        .toString()
                                    if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                                        num5 = num5.toInt().div(4).plus(1).toString()
                                    }
                                }
                                if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                                } else {
                                    num6 = num6.toInt().div(2).toString()
                                    if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                                        num6 = num6.toInt().div(4).plus(1).toString()
                                    }
                                }
                                if (num1.toInt() == num2.toInt()) num2 =
                                    num2.toInt().minus(1).toString()
                                if (num1.toInt() == num3.toInt()) num3 =
                                    num3.toInt().minus(3).toString()
                                if (num1.toInt() == num4.toInt()) num4 =
                                    num4.toInt().minus(2).toString()
                                if (num1.toInt() == num5.toInt()) num5 =
                                    num5.toInt().minus(1).toString()
                                if (num1.toInt() == num6.toInt()) num6 =
                                    num6.toInt().minus(3).toString()

                                if (num2.toInt() == num3.toInt()) num3 =
                                    num3.toInt().minus(1).toString()
                                if (num2.toInt() == num4.toInt()) num4 =
                                    num4.toInt().minus(3).toString()
                                if (num2.toInt() == num5.toInt()) num5 =
                                    num5.toInt().minus(1).toString()
                                if (num2.toInt() == num6.toInt()) num6 =
                                    num6.toInt().minus(3).toString()

                                if (num3.toInt() == num4.toInt()) num4 =
                                    num4.toInt().minus(2).toString()
                                if (num3.toInt() == num5.toInt()) num5 =
                                    num5.toInt().minus(1).toString()
                                if (num3.toInt() == num6.toInt()) num6 =
                                    num2.toInt().minus(1).toString()

                                if (num4.toInt() == num5.toInt()) num5 =
                                    num5.toInt().minus(2).toString()
                                if (num4.toInt() == num6.toInt()) num6 =
                                    num6.toInt().minus(3).toString()

                                if (num5.toInt() == num6.toInt()) num6 =
                                    num6.toInt().plus(4).toString()

                                var sixNumbers = SixNumber(num1, num2, num3, num4, num5, num6)
                                sixNumberList.add(sixNumbers)
                            }
                        }

                        sixNumberAdapter = SixNumberAdapter(sixNumberList)
                        binding.recyclerview6column.adapter = sixNumberAdapter
                    }
                }
                if (binding.tvColNo.text.toString() != "-" && binding.tvColNo.text.toString() == "5") {
                    if (binding.tvMaxNo.text.toString() == "-"
                        || layoutColFiveBinding.tv15.text.toString() == "-" || layoutColFiveBinding.tv25.text.toString() == "-" || layoutColFiveBinding.tv35.text.toString() == "-"
                        || layoutColFiveBinding.tv45.text.toString() == "-" || layoutColFiveBinding.tv55.text.toString() == "-" || layoutColFiveBinding.tv65.text.toString() == "-"
                        || layoutColFiveBinding.tv75.text.toString() == "-" || layoutColFiveBinding.tv85.text.toString() == "-" || layoutColFiveBinding.tv95.text.toString() == "-"
                        || layoutColFiveBinding.tv105.text.toString() == "-"
                    ) {
                        customToast("Result can't predict, please select number by clicking box.")
                        return
                    } else {
                        var string1: String? = null
                        var string2: String? = null
                        var string3: String? = null
                        var string4: String? = null
                        var string5: String? = null
                        var string6: String? = null
                        var string7: String? = null
                        var string8: String? = null
                        var string9: String? = null
                        var string10: String? = null

                        if (layoutColFiveBinding.tv15.text.toString().toInt() in 1..9) {
                            string1 = "11"
                        } else {
                            string1 = layoutColFiveBinding.tv15.text.toString()
                        }
                        if (layoutColFiveBinding.tv25.text.toString().toInt() in 1..9) {
                            string2 = "13"
                        } else {
                            string2 = layoutColFiveBinding.tv25.text.toString()
                        }
                        if (layoutColFiveBinding.tv35.text.toString().toInt() in 1..9) {
                            string3 = "15"
                        } else {
                            string3 = layoutColFiveBinding.tv35.text.toString()
                        }
                        if (layoutColFiveBinding.tv45.text.toString().toInt() in 1..9) {
                            string4 = "17"
                        } else {
                            string4 = layoutColFiveBinding.tv45.text.toString()
                        }
                        if (layoutColFiveBinding.tv55.text.toString().toInt() in 1..9) {
                            string5 = "19"
                        } else {
                            string5 = layoutColFiveBinding.tv55.text.toString()
                        }
                        if (layoutColFiveBinding.tv65.text.toString().toInt() in 1..9) {
                            string6 = "21"
                        } else {
                            string6 = layoutColFiveBinding.tv65.text.toString()
                        }
                        if (layoutColFiveBinding.tv75.text.toString().toInt() in 1..9) {
                            string7 = "23"
                        } else {
                            string7 = layoutColFiveBinding.tv75.text.toString()
                        }
                        if (layoutColFiveBinding.tv85.text.toString().toInt() in 1..9) {
                            string8 = "25"
                        } else {
                            string8 = layoutColFiveBinding.tv85.text.toString()
                        }
                        if (layoutColFiveBinding.tv95.text.toString().toInt() in 1..9) {
                            string9 = "27"
                        } else {
                            string9 = layoutColFiveBinding.tv95.text.toString()
                        }
                        if (layoutColFiveBinding.tv105.text.toString().toInt() in 1..9) {
                            string10 = "29"
                        } else {
                            string10 = layoutColFiveBinding.tv105.text.toString()
                        }

                        sumCol16 = Integer.parseInt(string1).plus(Integer.parseInt(string6))
                        sumCol27 = Integer.parseInt(string2).plus(Integer.parseInt(string7))
                        sumCol38 = Integer.parseInt(string3).plus(Integer.parseInt(string8))
                        sumCol49 = Integer.parseInt(string4).plus(Integer.parseInt(string9))
                        sumCol510 = Integer.parseInt(string5).plus(Integer.parseInt(string10))

                        var sumDigits16: Int = getSumOfDigits(sumCol16!!)
                        var sumDigits27: Int = getSumOfDigits(sumCol27!!)
                        var sumDigits38: Int = getSumOfDigits(sumCol38!!)
                        var sumDigits49: Int = getSumOfDigits(sumCol49!!)
                        var sumDigits510: Int = getSumOfDigits(sumCol510!!)

                        val powSumDigits16 =
                            BigInteger(sumDigits16.toString()).pow(80)
                        val powSumDigits27 =
                            BigInteger(sumDigits27.toString()).pow(80)
                        val powSumDigits38 =
                            BigInteger(sumDigits38.toString()).pow(80)
                        val powSumDigits49 =
                            BigInteger(sumDigits49.toString()).pow(80)
                        val powSumDigits510 =
                            BigInteger(sumDigits510.toString()).pow(80)

                        val result17 =
                            powSumDigits16.toString().replace("0", "7").map { it.toString() }.toTypedArray()
                        val result28 =
                            powSumDigits27.toString().replace("0", "8").map { it.toString() }.toTypedArray()
                        val result39 =
                            powSumDigits38.toString().replace("0", "9").map { it.toString() }.toTypedArray()
                        val result410 =
                            powSumDigits49.toString().replace("0", "1").map { it.toString() }.toTypedArray()
                        val result511 =
                            powSumDigits510.toString().replace("0", "2").map { it.toString() }.toTypedArray()

                        var num1: String? = null
                        var num2: String? = null
                        var num3: String? = null
                        var num4: String? = null
                        var num5: String? = null

                        fiveNumberList.clear()
                        for (i in 1..2) {
                            if (i == 1) {
                                num1 = result17[25] + result17[26]
                                num2 = result28[26] + result28[33]
                                num3 = result39[33] + result39[34]
                                num4 = result410[34] + result410[35]
                                num5 = result511[35] + result511[26]
                                if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                                } else {
                                    num1 = num1.toInt().div(2).plus(1).toString()
                                    if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                                        num1 = num1.toInt().div(4).plus(1).toString()
                                    }

                                }
                                if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                                } else {
                                    num2 = num2.toInt().div(2).toString()
                                    if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                                        num2 = num2.toInt().div(4).plus(1).toString()
                                    }
                                }
                                if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                                } else {
                                    num3 = num3.toInt().div(2)
                                        .toString()
                                    if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                                        num3 = num3.toInt().div(4).plus(1).toString()
                                    }
                                }
                                if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                                } else {
                                    num4 = num4.toInt().div(2)
                                        .toString()
                                    if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                                        num4 = num4.toInt().div(4).plus(1).toString()
                                    }
                                }
                                if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                                } else {
                                    num5 = binding.tvMaxNo.text.toString().toInt()!!.minus(1)
                                        .toString()
                                    if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                                        num5 = num5.toInt().div(4).plus(1).toString()
                                    }
                                }

                                if (num1.toInt() == num2.toInt()) num2 =
                                    num2.toInt().plus(4).toString()
                                if (num1.toInt() == num3.toInt()) num3 =
                                    num3.toInt().plus(1).toString()
                                if (num1.toInt() == num4.toInt()) num4 =
                                    num4.toInt().div(2).toString()
                                if (num1.toInt() == num5.toInt()) num5 =
                                    num5.toInt().plus(3).toString()

                                if (num2.toInt() == num3.toInt()) num3 =
                                    num3.toInt().plus(5).toString()
                                if (num2.toInt() == num4.toInt()) num4 =
                                    num4.toInt().plus(6).toString()
                                if (num2.toInt() == num5.toInt()) num5 =
                                    num5.toInt().plus(7).toString()

                                if (num3.toInt() == num4.toInt()) num4 =
                                    num4.toInt().plus(1).toString()
                                if (num3.toInt() == num5.toInt()) num5 =
                                    num5.toInt().div(2).toString()

                                if (num4.toInt() == num5.toInt()) num5 =
                                    num5.toInt().plus(4).toString()

                                var sixNumbers = FiveNumber(num1, num2, num3, num4, num5)
                                fiveNumberList.add(sixNumbers)
                            }
                            if (i == 2) {
                                num1 = result28[8] + result28[9]
                                num2 = result28[8] + result28[9]
                                num3 = result39[9] + result39[33]
                                num4 = result410[33] + result410[11]
                                num5 = result511[11] + result511[12]
                                if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                                } else {
                                    num1 = num1.toInt().div(2).plus(1).toString()
                                    if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                                        num1 = num1.toInt().div(4).plus(1).toString()
                                    }

                                }
                                if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                                } else {
                                    num2 = num2.toInt().div(2).toString()
                                    if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                                        num2 = num2.toInt().div(4).plus(1).toString()
                                    }
                                }
                                if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                                } else {
                                    num3 = num3.toInt().div(2)
                                        .toString()
                                    if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                                        num3 = num3.toInt().div(4).plus(1).toString()
                                    }
                                }
                                if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                                } else {
                                    num4 = binding.tvMaxNo.text.toString().toInt()!!.minus(3)
                                        .toString()
                                    if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                                        num4 = num4.toInt().div(4).plus(1).toString()
                                    }
                                }
                                if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                                } else {
                                    num5 = binding.tvMaxNo.text.toString().toInt()!!.minus(2)
                                        .toString()
                                    if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                                        num5 = num5.toInt().div(4).plus(1).toString()
                                    }
                                }

                                if (num1.toInt() == num2.toInt()) num2 =
                                    num2.toInt().minus(1).toString()
                                if (num1.toInt() == num3.toInt()) num3 =
                                    num3.toInt().minus(3).toString()
                                if (num1.toInt() == num4.toInt()) num4 =
                                    num4.toInt().minus(2).toString()
                                if (num1.toInt() == num5.toInt()) num5 =
                                    num5.toInt().minus(1).toString()

                                if (num2.toInt() == num3.toInt()) num3 =
                                    num3.toInt().minus(1).toString()
                                if (num2.toInt() == num4.toInt()) num4 =
                                    num4.toInt().minus(3).toString()
                                if (num2.toInt() == num5.toInt()) num5 =
                                    num5.toInt().minus(1).toString()

                                if (num3.toInt() == num4.toInt()) num4 =
                                    num4.toInt().minus(2).toString()
                                if (num3.toInt() == num5.toInt()) num5 =
                                    num5.toInt().minus(1).toString()

                                if (num4.toInt() == num5.toInt()) num5 =
                                    num5.toInt().minus(2).toString()

                                var sixNumbers = FiveNumber(num1, num2, num3, num4, num5)
                                fiveNumberList.add(sixNumbers)
                            }
                        }

                        fiveNumberAdapter = FiveNumberAdapter(fiveNumberList)
                        binding.recyclerview6column.adapter = fiveNumberAdapter
                    }
                }
                if (binding.tvColNo.text.toString() != "-" && binding.tvColNo.text.toString() == "7") {
                    if (binding.tvMaxNo.text.toString() == "-"
                        || layoutColSevenBinding.tv17.text.toString() == "-" || layoutColSevenBinding.tv27.text.toString() == "-" || layoutColSevenBinding.tv37.text.toString() == "-"
                        || layoutColSevenBinding.tv47.text.toString() == "-" || layoutColSevenBinding.tv57.text.toString() == "-" || layoutColSevenBinding.tv67.text.toString() == "-"
                        || layoutColSevenBinding.tv77.text.toString() == "-" || layoutColSevenBinding.tv87.text.toString() == "-" || layoutColSevenBinding.tv97.text.toString() == "-"
                        || layoutColSevenBinding.tv107.text.toString() == "-" || layoutColSevenBinding.tv117.text.toString() == "-" || layoutColSevenBinding.tv127.text.toString() == "-"
                        || layoutColSevenBinding.tv137.text.toString() == "-" || layoutColSevenBinding.tv147.text.toString() == "-"
                    ) {
                        customToast("Result can't predict, please select number by clicking box.")
                        return
                    } else {
                        var string1: String? = null
                        var string2: String? = null
                        var string3: String? = null
                        var string4: String? = null
                        var string5: String? = null
                        var string6: String? = null
                        var string7: String? = null
                        var string8: String? = null
                        var string9: String? = null
                        var string10: String? = null
                        var string11: String? = null
                        var string12: String? = null
                        var string13: String? = null
                        var string14: String? = null

                        if (layoutColSevenBinding.tv17.text.toString().toInt() in 1..9) {
                            string1 = "11"
                        } else {
                            string1 = layoutColSevenBinding.tv17.text.toString()
                        }
                        if (layoutColSevenBinding.tv27.text.toString().toInt() in 1..9) {
                            string2 = "13"
                        } else {
                            string2 = layoutColSevenBinding.tv27.text.toString()
                        }
                        if (layoutColSevenBinding.tv37.text.toString().toInt() in 1..9) {
                            string3 = "15"
                        } else {
                            string3 = layoutColSevenBinding.tv37.text.toString()
                        }
                        if (layoutColSevenBinding.tv47.text.toString().toInt() in 1..9) {
                            string4 = "17"
                        } else {
                            string4 = layoutColSevenBinding.tv47.text.toString()
                        }
                        if (layoutColSevenBinding.tv57.text.toString().toInt() in 1..9) {
                            string5 = "19"
                        } else {
                            string5 = layoutColSevenBinding.tv57.text.toString()
                        }
                        if (layoutColSevenBinding.tv67.text.toString().toInt() in 1..9) {
                            string6 = "21"
                        } else {
                            string6 = layoutColSevenBinding.tv67.text.toString()
                        }
                        if (layoutColSevenBinding.tv77.text.toString().toInt() in 1..9) {
                            string7 = "23"
                        } else {
                            string7 = layoutColSevenBinding.tv77.text.toString()
                        }
                        if (layoutColSevenBinding.tv87.text.toString().toInt() in 1..9) {
                            string8 = "25"
                        } else {
                            string8 = layoutColSevenBinding.tv87.text.toString()
                        }
                        if (layoutColSevenBinding.tv97.text.toString().toInt() in 1..9) {
                            string9 = "27"
                        } else {
                            string9 = layoutColSevenBinding.tv97.text.toString()
                        }
                        if (layoutColSevenBinding.tv107.text.toString().toInt() in 1..9) {
                            string10 = "29"
                        } else {
                            string10 = layoutColSevenBinding.tv107.text.toString()
                        }
                        if (layoutColSevenBinding.tv117.text.toString().toInt() in 1..9) {
                            string11 = "31"
                        } else {
                            string11 = layoutColSevenBinding.tv117.text.toString()
                        }
                        if (layoutColSevenBinding.tv127.text.toString().toInt() in 1..9) {
                            string12 = "33"
                        } else {
                            string12 = layoutColSevenBinding.tv127.text.toString()
                        }
                        if (layoutColSevenBinding.tv137.text.toString().toInt() in 1..9) {
                            string13 = "17"
                        } else {
                            string13 = layoutColSevenBinding.tv137.text.toString()
                        }
                        if (layoutColSevenBinding.tv147.text.toString().toInt() in 1..9) {
                            string14 = "19"
                        } else {
                            string14 = layoutColSevenBinding.tv147.text.toString()
                        }
                        sumCol18 = Integer.parseInt(string1).plus(Integer.parseInt(string8))
                        sumCol29 = Integer.parseInt(string2).plus(Integer.parseInt(string9))
                        sumCol310 = Integer.parseInt(string3).plus(Integer.parseInt(string10))
                        sumCol411 = Integer.parseInt(string4).plus(Integer.parseInt(string11))
                        sumCol512 = Integer.parseInt(string5).plus(Integer.parseInt(string12))
                        sumCol613 = Integer.parseInt(string6).plus(Integer.parseInt(string13))
                        sumCol714 = Integer.parseInt(string7).plus(Integer.parseInt(string14))


                        var sumDigits18: Int = getSumOfDigits(sumCol18!!)
                        var sumDigits29: Int = getSumOfDigits(sumCol29!!)
                        var sumDigits310: Int = getSumOfDigits(sumCol310!!)
                        var sumDigits411: Int = getSumOfDigits(sumCol411!!)
                        var sumDigits512: Int = getSumOfDigits(sumCol512!!)
                        var sumDigits613: Int = getSumOfDigits(sumCol613!!)
                        var sumDigits714: Int = getSumOfDigits(sumCol714!!)

                        val powSumDigits18 =
                            BigInteger(sumDigits18.toString()).pow(80)
                        val powSumDigits29 =
                            BigInteger(sumDigits29.toString()).pow(80)
                        val powSumDigits310 =
                            BigInteger(sumDigits310.toString()).pow(80)
                        val powSumDigits411 =
                            BigInteger(sumDigits411.toString()).pow(80)
                        val powSumDigits512 =
                            BigInteger(sumDigits512.toString()).pow(80)
                        val powSumDigits613 =
                            BigInteger(sumDigits613.toString()).pow(80)
                        val powSumDigits714 =
                            BigInteger(sumDigits714.toString()).pow(80)

                        val result17 =
                            powSumDigits18.toString().replace("0", "7").map { it.toString() }.toTypedArray()
                        val result28 =
                            powSumDigits29.toString().replace("0", "8").map { it.toString() }.toTypedArray()
                        val result39 =
                            powSumDigits310.toString().replace("0", "9").map { it.toString() }.toTypedArray()
                        val result410 =
                            powSumDigits411.toString().replace("0", "1").map { it.toString() }.toTypedArray()
                        val result511 =
                            powSumDigits512.toString().replace("0", "1").map { it.toString() }.toTypedArray()
                        val result612 =
                            powSumDigits613.toString().replace("0", "2").map { it.toString() }.toTypedArray()
                        val result714 =
                            powSumDigits714.toString().map { it.toString() }.toTypedArray()

                        var num1: String? = null
                        var num2: String? = null
                        var num3: String? = null
                        var num4: String? = null
                        var num5: String? = null
                        var num6: String? = null
                        var num7: String? = null

                        sevenNumberList.clear()
                        for (i in 1..2) {
                            if (i == 1) {
                                num1 = result17[25] + result17[26]
                                num2 = result28[26] + result28[33]
                                num3 = result39[33] + result39[34]
                                num4 = result410[34] + result410[35]
                                num5 = result511[35] + result511[26]
                                num6 = result612[26] + result612[25]
                                num7 = result714[12] + result714[13]
                                if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                                } else {
                                    num1 = num1.toInt().div(2).plus(1).toString()
                                    if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                                        num1 = num1.toInt().div(4).plus(1).toString()
                                    }

                                }
                                if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                                } else {
                                    num2 = num2.toInt().div(2).toString()
                                    if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                                        num2 = num2.toInt().div(4).plus(1).toString()
                                    }
                                }
                                if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                                } else {
                                    num3 = num3.toInt().div(2)
                                        .toString()
                                    if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                                        num3 = num3.toInt().div(4).plus(1).toString()
                                    }
                                }
                                if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                                } else {
                                    num4 = num4.toInt().div(2)
                                        .toString()
                                    if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                                        num4 = num4.toInt().div(4).plus(1).toString()
                                    }
                                }
                                if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                                } else {
                                    num5 = binding.tvMaxNo.text.toString().toInt()!!.minus(1)
                                        .toString()
                                    if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                                        num5 = num5.toInt().div(4).plus(1).toString()
                                    }
                                }
                                if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                                } else {
                                    num6 = num6.toInt().div(2).toString()
                                    if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                                        num6 = num6.toInt().div(4).plus(1).toString()
                                    }
                                }
                                if (num1.toInt() == num2.toInt()) num2 =
                                    num2.toInt().plus(4).toString()
                                if (num1.toInt() == num3.toInt()) num3 =
                                    num3.toInt().plus(1).toString()
                                if (num1.toInt() == num4.toInt()) num4 =
                                    num4.toInt().div(2).toString()
                                if (num1.toInt() == num5.toInt()) num5 =
                                    num5.toInt().plus(3).toString()
                                if (num1.toInt() == num6.toInt()) num6 =
                                    num6.toInt().plus(4).toString()

                                if (num2.toInt() == num3.toInt()) num3 =
                                    num3.toInt().plus(5).toString()
                                if (num2.toInt() == num4.toInt()) num4 =
                                    num4.toInt().plus(6).toString()
                                if (num2.toInt() == num5.toInt()) num5 =
                                    num5.toInt().plus(7).toString()
                                if (num2.toInt() == num6.toInt()) num6 =
                                    num6.toInt().plus(8).toString()

                                if (num3.toInt() == num4.toInt()) num4 =
                                    num4.toInt().plus(1).toString()
                                if (num3.toInt() == num5.toInt()) num5 =
                                    num5.toInt().div(2).toString()
                                if (num3.toInt() == num6.toInt()) num6 =
                                    num2.toInt().plus(3).toString()

                                if (num4.toInt() == num5.toInt()) num5 =
                                    num5.toInt().plus(4).toString()
                                if (num4.toInt() == num6.toInt()) num6 =
                                    num6.toInt().plus(5).toString()

                                if (num5.toInt() == num6.toInt()) num6 =
                                    num6.toInt().plus(6).toString()

                                var sixNumbers =
                                    SevenNumber(num1, num2, num3, num4, num5, num6, num7)
                                sevenNumberList.add(sixNumbers)
                            }
                            if (i == 2) {
                                num1 = result17[8] + result17[9]
                                num2 = result28[8] + result28[9]
                                num3 = result39[9] + result39[33]
                                num4 = result410[33] + result410[11]
                                num5 = result511[11] + result612[12]
                                num6 = result612[12] + result511[27]
                                num7 = result714[2] + result714[9]
                                if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                                } else {
                                    num1 = num1.toInt().div(2).plus(1).toString()
                                    if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                                        num1 = num1.toInt().div(4).plus(1).toString()
                                    }

                                }
                                if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                                } else {
                                    num2 = num2.toInt().div(2).toString()
                                    if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                                        num2 = num2.toInt().div(4).plus(1).toString()
                                    }
                                }
                                if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                                } else {
                                    num3 = num3.toInt().div(2)
                                        .toString()
                                    if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                                        num3 = num3.toInt().div(4).plus(1).toString()
                                    }
                                }
                                if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                                } else {
                                    num4 = binding.tvMaxNo.text.toString().toInt()!!.minus(3)
                                        .toString()
                                    if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                                        num4 = num4.toInt().div(4).plus(1).toString()
                                    }
                                }
                                if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                                } else {
                                    num5 = binding.tvMaxNo.text.toString().toInt()!!.minus(2)
                                        .toString()
                                    if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                                        num5 = num5.toInt().div(4).plus(1).toString()
                                    }
                                }
                                if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                                } else {
                                    num6 = num6.toInt().div(2).toString()
                                    if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                                        num6 = num6.toInt().div(4).plus(1).toString()
                                    }
                                }
                                if (num1.toInt() == num2.toInt()) num2 =
                                    num2.toInt().minus(1).toString()
                                if (num1.toInt() == num3.toInt()) num3 =
                                    num3.toInt().minus(3).toString()
                                if (num1.toInt() == num4.toInt()) num4 =
                                    num4.toInt().minus(2).toString()
                                if (num1.toInt() == num5.toInt()) num5 =
                                    num5.toInt().minus(1).toString()
                                if (num1.toInt() == num6.toInt()) num6 =
                                    num6.toInt().minus(3).toString()

                                if (num2.toInt() == num3.toInt()) num3 =
                                    num3.toInt().minus(1).toString()
                                if (num2.toInt() == num4.toInt()) num4 =
                                    num4.toInt().minus(3).toString()
                                if (num2.toInt() == num5.toInt()) num5 =
                                    num5.toInt().minus(1).toString()
                                if (num2.toInt() == num6.toInt()) num6 =
                                    num6.toInt().minus(3).toString()

                                if (num3.toInt() == num4.toInt()) num4 =
                                    num4.toInt().minus(2).toString()
                                if (num3.toInt() == num5.toInt()) num5 =
                                    num5.toInt().minus(1).toString()
                                if (num3.toInt() == num6.toInt()) num6 =
                                    num2.toInt().minus(1).toString()

                                if (num4.toInt() == num5.toInt()) num5 =
                                    num5.toInt().minus(2).toString()
                                if (num4.toInt() == num6.toInt()) num6 =
                                    num6.toInt().minus(3).toString()

                                if (num5.toInt() == num6.toInt()) num6 =
                                    num6.toInt().plus(4).toString()

                                var sixNumbers =
                                    SevenNumber(num1, num2, num3, num4, num5, num6, num7)
                                sevenNumberList.add(sixNumbers)
                            }
                        }

                        sevenNumberAdapter = SevenNumberAdapter(sevenNumberList)
                        binding.recyclerview6column.adapter = sevenNumberAdapter
                    }
                }

            }

            R.id.btn_gen_40nos -> {
                binding.recyclerview6column.visibility = View.VISIBLE
                if (GooglePlayBillingPreferences.isPurchased()) {
                    if (binding.tvColNo.text.toString() != "-" && binding.tvColNo.text.toString() == "6") {
                        generate40sRows()
                        return
                    }
                    if (binding.tvColNo.text.toString() != "-" && binding.tvColNo.text.toString() == "5") {
                        generate40sRowFor5()
                        return
                    }
                    if (binding.tvColNo.text.toString() != "-" && binding.tvColNo.text.toString() == "7") {
                        generate40sRowFor7()
                        return
                    }
                } else {
                    billingClient.startConnection(object : BillingClientStateListener {
                        override fun onBillingSetupFinished(billingResult: BillingResult) {
                            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                                val params = SkuDetailsParams.newBuilder()
                                params.setSkusList(skulList)
                                    .setType(BillingClient.SkuType.INAPP)

                                billingClient.querySkuDetailsAsync(params.build()) { billingResult, skuDetailList ->

                                    for (skuDetail in skuDetailList!!) {
                                        val flowPurchase = BillingFlowParams.newBuilder()
                                            .setSkuDetails(skuDetail)
                                            .build()

                                        val responseCode = billingClient.launchBillingFlow(
                                            this@LottoActivity,
                                            flowPurchase
                                        ).responseCode
                                        if (responseCode == 0) {
                                            GooglePlayBillingPreferences.setPurchased(true)
//                                            binding.btnGen40nos.text = "Generate 40's Lines/Rows"
                                        }else{
                                            GooglePlayBillingPreferences.setPurchased(true)
                                        }
                                    }
                                }
                            }
                        }

                        override fun onBillingServiceDisconnected() {
                            // Try to restart the connection on the next request to
                            // Google Play by calling the startConnection() method.
                            GooglePlayBillingPreferences.setPurchased(false)
                        }
                    })
                }
            }

        }
    }

    /* function to get sum of digits */
    fun getSumOfDigits(number: Int): Int {
        var number = number
        var sum = 0
        while (number > 0) {
            val r = number % 10
            sum += r
            number /= 10
        }
        return sum
    }

    private fun generate40sRows() {
        if (binding.tvMaxNo.text.toString() == "-"
            || layoutColSixBinding.tv16.text.toString() == "-" || layoutColSixBinding.tv26.text.toString() == "-" || layoutColSixBinding.tv36.text.toString() == "-"
            || layoutColSixBinding.tv46.text.toString() == "-" || layoutColSixBinding.tv56.text.toString() == "-" || layoutColSixBinding.tv66.text.toString() == "-"
            || layoutColSixBinding.tv76.text.toString() == "-" || layoutColSixBinding.tv86.text.toString() == "-" || layoutColSixBinding.tv96.text.toString() == "-"
            || layoutColSixBinding.tv106.text.toString() == "-" || layoutColSixBinding.tv116.text.toString() == "-" || layoutColSixBinding.tv126.text.toString() == "-"
        ) {
            customToast("Result can't predict, please select number by clicking box.")
            return
        } else {
            var string1: String? = null
            var string2: String? = null
            var string3: String? = null
            var string4: String? = null
            var string5: String? = null
            var string6: String? = null
            var string7: String? = null
            var string8: String? = null
            var string9: String? = null
            var string10: String? = null
            var string11: String? = null
            var string12: String? = null

            if (layoutColSixBinding.tv16.text.toString().toInt() in 1..9) {
                string1 = "11"
            } else {
                string1 = layoutColSixBinding.tv16.text.toString()
            }
            if (layoutColSixBinding.tv26.text.toString().toInt() in 1..9) {
                string2 = "13"
            } else {
                string2 = layoutColSixBinding.tv26.text.toString()
            }
            if (layoutColSixBinding.tv36.text.toString().toInt() in 1..9) {
                string3 = "15"
            } else {
                string3 = layoutColSixBinding.tv36.text.toString()
            }
            if (layoutColSixBinding.tv46.text.toString().toInt() in 1..9) {
                string4 = "17"
            } else {
                string4 = layoutColSixBinding.tv46.text.toString()
            }
            if (layoutColSixBinding.tv56.text.toString().toInt() in 1..9) {
                string5 = "19"
            } else {
                string5 = layoutColSixBinding.tv56.text.toString()
            }
            if (layoutColSixBinding.tv66.text.toString().toInt() in 1..9) {
                string6 = "21"
            } else {
                string6 = layoutColSixBinding.tv66.text.toString()
            }
            if (layoutColSixBinding.tv76.text.toString().toInt() in 1..9) {
                string7 = "23"
            } else {
                string7 = layoutColSixBinding.tv76.text.toString()
            }
            if (layoutColSixBinding.tv86.text.toString().toInt() in 1..9) {
                string8 = "25"
            } else {
                string8 = layoutColSixBinding.tv86.text.toString()
            }
            if (layoutColSixBinding.tv96.text.toString().toInt() in 1..9) {
                string9 = "27"
            } else {
                string9 = layoutColSixBinding.tv96.text.toString()
            }
            if (layoutColSixBinding.tv106.text.toString().toInt() in 1..9) {
                string10 = "29"
            } else {
                string10 = layoutColSixBinding.tv106.text.toString()
            }
            if (layoutColSixBinding.tv116.text.toString().toInt() in 1..9) {
                string11 = "31"
            } else {
                string11 = layoutColSixBinding.tv116.text.toString()
            }
            if (layoutColSixBinding.tv126.text.toString().toInt() in 1..9) {
                string12 = "33"
            } else {
                string12 = layoutColSixBinding.tv126.text.toString()
            }
            sumCol17 = Integer.parseInt(string1).plus(Integer.parseInt(string7))
            sumCol28 = Integer.parseInt(string2).plus(Integer.parseInt(string8))
            sumCol39 = Integer.parseInt(string3).plus(Integer.parseInt(string9))
            sumCol410 = Integer.parseInt(string4).plus(Integer.parseInt(string10))
            sumCol511 = Integer.parseInt(string5).plus(Integer.parseInt(string11))
            sumCol612 = Integer.parseInt(string6).plus(Integer.parseInt(string12))

            var sumDigits17: Int = getSumOfDigits(sumCol17!!)
            var sumDigits28: Int = getSumOfDigits(sumCol28!!)
            var sumDigits39: Int = getSumOfDigits(sumCol39!!)
            var sumDigits410: Int = getSumOfDigits(sumCol410!!)
            var sumDigits511: Int = getSumOfDigits(sumCol511!!)
            var sumDigits612: Int = getSumOfDigits(sumCol612!!)

            val powSumDigits17 = BigInteger(sumDigits17.toString()).pow(80)
            val powSumDigits28 = BigInteger(sumDigits28.toString()).pow(80)
            val powSumDigits39 = BigInteger(sumDigits39.toString()).pow(80)
            val powSumDigits410 = BigInteger(sumDigits410.toString()).pow(80)
            val powSumDigits511 = BigInteger(sumDigits511.toString()).pow(80)
            val powSumDigits612 = BigInteger(sumDigits612.toString()).pow(80)

            val result17 = powSumDigits17.toString().replace("0", "7").map { it.toString() }.toTypedArray()
            val result28 = powSumDigits28.toString().replace("0", "8").map { it.toString() }.toTypedArray()
            val result39 = powSumDigits39.toString().replace("0", "9").map { it.toString() }.toTypedArray()
            val result410 = powSumDigits410.toString().replace("0", "1").map { it.toString() }.toTypedArray()
            val result511 = powSumDigits511.toString().replace("0", "2").map { it.toString() }.toTypedArray()
            val result612 = powSumDigits612.toString().replace("0", "3").map { it.toString() }.toTypedArray()

            var num1: String? = null
            var num2: String? = null
            var num3: String? = null
            var num4: String? = null
            var num5: String? = null
            var num6: String? = null

            if (result17.size == 1 || result28.size == 1 || result39.size == 1 || result410.size == 1 || result511.size == 1 || result612.size == 1) {
                customToast("Result can't predict, please select number by clicking box.")
                return
            }
            sixNumberList.clear()

            for (i in 1..40) {
                if (i == 1) {
                    num1 = result17[25] + result17[26]
                    num2 = result28[26] + result28[33]
                    num3 = result39[33] + result39[34]
                    num4 = result410[34] + result410[35]
                    num5 = result511[35] + result511[26]
                    num6 = result612[26] + result612[25]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }

                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2)
                            .toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2)
                            .toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = binding.tvMaxNo.text.toString().toInt()!!.minus(1)
                            .toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().plus(4).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().plus(1).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().div(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().plus(3).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().plus(5).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().plus(6).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().plus(7).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().plus(8).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().plus(1).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().div(2).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().plus(3).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().plus(4).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().plus(5).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(6).toString()

                    var sixNumbers = SixNumber(num1, num2, num3, num4, num5, num6)
                    sixNumberList.add(sixNumbers)
                }
                if (i == 2) {
                    num1 = result28[8] + result28[9]
                    num2 = result28[8] + result28[9]
                    num3 = result39[9] + result39[33]
                    num4 = result410[33] + result410[11]
                    num5 = result511[11] + result612[12]
                    num6 = result612[12] + result511[27]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }

                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2)
                            .toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = binding.tvMaxNo.text.toString().toInt()!!.minus(3)
                            .toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = binding.tvMaxNo.text.toString().toInt()!!.minus(2)
                            .toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sixNumbers = SixNumber(num1, num2, num3, num4, num5, num6)
                    sixNumberList.add(sixNumbers)
                }
                if (i == 3) {
                    num1 = result17[13] + result17[17]
                    num2 = result28[14] + result28[23]
                    num3 = result39[15] + result39[16]
                    num4 = result410[16] + result410[17]
                    num5 = result511[17] + result612[18]
                    num6 = result612[18] + result511[19]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sixNumbers = SixNumber(num1, num2, num3, num4, num5, num6)
                    sixNumberList.add(sixNumbers)
                }
                if (i == 4) {
                    num1 = result17[19] + result17[36]
                    num2 = result28[36] + result28[1]
                    num3 = result39[21] + result39[22]
                    num4 = result410[22] + result410[23]
                    num5 = result511[23] + result612[24]
                    num6 = result612[24] + result511[25]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sixNumbers = SixNumber(num1, num2, num3, num4, num5, num6)
                    sixNumberList.add(sixNumbers)
                }
                if (i == 5) {
                    num1 = result17[24] + result17[27]
                    num2 = result28[23] + result28[24]
                    num3 = result39[22] + result39[21]
                    num4 = result410[21] + result410[36]
                    num5 = result511[36] + result612[19]
                    num6 = result612[19] + result511[26]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sixNumbers = SixNumber(num1, num2, num3, num4, num5, num6)
                    sixNumberList.add(sixNumbers)
                }
                if (i == 6) {
                    num1 = result17[18] + result17[27]
                    num2 = result28[27] + result28[8]
                    num3 = result39[16] + result39[15]
                    num4 = result410[15] + result410[13]
                    num5 = result511[14] + result612[13]
                    num6 = result612[29] + result511[30]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sixNumbers = SixNumber(num1, num2, num3, num4, num5, num6)
                    sixNumberList.add(sixNumbers)
                }
                if (i == 7) {
                    num1 = result17[12] + result17[7]
                    num2 = result28[11] + result28[33]
                    num3 = result39[33] + result39[19]
                    num4 = result410[25] + result410[28]
                    num5 = result511[18] + result612[27]
                    num6 = result612[17] + result511[28]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sixNumbers = SixNumber(num1, num2, num3, num4, num5, num6)
                    sixNumberList.add(sixNumbers)
                }
                if (i == 8) {
                    num1 = result17[16] + result17[36]
                    num2 = result28[15] + result28[19]
                    num3 = result39[14] + result39[23]
                    num4 = result410[23] + result410[22]
                    num5 = result511[27] + result612[18]
                    num6 = result612[14] + result511[13]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sixNumbers = SixNumber(num1, num2, num3, num4, num5, num6)
                    sixNumberList.add(sixNumbers)
                }
                if (i == 9) {
                    num1 = result17[11] + result17[13]
                    num2 = result28[18] + result28[15]
                    num3 = result39[15] + result39[17]
                    num4 = result410[27] + result410[9]
                    num5 = result511[9] + result612[11]
                    num6 = result612[11] + result511[27]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sixNumbers = SixNumber(num1, num2, num3, num4, num5, num6)
                    sixNumberList.add(sixNumbers)
                }
                if (i == 10) {
                    num1 = result17[11] + result17[13]
                    num2 = result28[13] + result28[16]
                    num3 = result39[15] + result39[17]
                    num4 = result410[17] + result410[19]
                    num5 = result511[19] + result612[12]
                    num6 = result612[21] + result511[19]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sixNumbers = SixNumber(num1, num2, num3, num4, num5, num6)
                    sixNumberList.add(sixNumbers)
                }
                if (i == 11) {
                    num1 = result17[21] + result17[28]
                    num2 = result28[23] + result28[27]
                    num3 = result39[25] + result39[33]
                    num4 = result410[33] + result410[35]
                    num5 = result511[35] + result612[27]
                    num6 = result612[27] + result511[35]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sixNumbers = SixNumber(num1, num2, num3, num4, num5, num6)
                    sixNumberList.add(sixNumbers)
                }
                if (i == 12) {
                    num1 = result17[27] + result17[33]
                    num2 = result28[30] + result28[23]
                    num3 = result39[13] + result39[16]
                    num4 = result410[16] + result410[19]
                    num5 = result511[19] + result612[22]
                    num6 = result612[22] + result511[19]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sixNumbers = SixNumber(num1, num2, num3, num4, num5, num6)
                    sixNumberList.add(sixNumbers)
                }
                if (i == 13) {
                    num1 = result17[21] + result17[24]
                    num2 = result28[26] + result28[25]
                    num3 = result39[25] + result39[26]
                    num4 = result410[26] + result410[33]
                    num5 = result511[33] + result612[34]
                    num6 = result612[34] + result511[25]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sixNumbers = SixNumber(num1, num2, num3, num4, num5, num6)
                    sixNumberList.add(sixNumbers)
                }
                if (i == 14) {
                    num1 = result17[16] + result17[34]
                    num2 = result28[4] + result28[26]
                    num3 = result39[26] + result39[26]
                    num4 = result410[8] + result410[18]
                    num5 = result511[18] + result612[12]
                    num6 = result612[12] + result511[26]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sixNumbers = SixNumber(num1, num2, num3, num4, num5, num6)
                    sixNumberList.add(sixNumbers)
                }
                if (i == 15) {
                    num1 = result17[2] + result17[14]
                    num2 = result28[14] + result28[18]
                    num3 = result39[16] + result39[18]
                    num4 = result410[18] + result410[36]
                    num5 = result511[36] + result612[22]
                    num6 = result612[22] + result511[21]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sixNumbers = SixNumber(num1, num2, num3, num4, num5, num6)
                    sixNumberList.add(sixNumbers)
                }
                if (i == 16) {
                    num1 = result17[24] + result17[18]
                    num2 = result28[28] + result28[12]
                    num3 = result39[12] + result39[26]
                    num4 = result410[26] + result410[26]
                    num5 = result511[26] + result612[25]
                    num6 = result612[25] + result511[26]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sixNumbers = SixNumber(num1, num2, num3, num4, num5, num6)
                    sixNumberList.add(sixNumbers)
                }
                if (i == 17) {
                    num1 = result17[33] + result17[26]
                    num2 = result28[26] + result28[9]
                    num3 = result39[9] + result39[12]
                    num4 = result410[12] + result410[15]
                    num5 = result511[15] + result612[18]
                    num6 = result612[18] + result511[17]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sixNumbers = SixNumber(num1, num2, num3, num4, num5, num6)
                    sixNumberList.add(sixNumbers)
                }
                if (i == 18) {
                    num1 = result17[34] + result17[8]
                    num2 = result28[8] + result28[12]
                    num3 = result39[12] + result39[16]
                    num4 = result410[16] + result410[36]
                    num5 = result511[36] + result612[24]
                    num6 = result612[24] + result511[26]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sixNumbers = SixNumber(num1, num2, num3, num4, num5, num6)
                    sixNumberList.add(sixNumbers)
                }
                if (i == 19) {
                    num1 = result17[24] + result17[36]
                    num2 = result28[36] + result28[16]
                    num3 = result39[16] + result39[12]
                    num4 = result410[12] + result410[8]
                    num5 = result511[8] + result612[34]
                    num6 = result612[34] + result511[8]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sixNumbers = SixNumber(num1, num2, num3, num4, num5, num6)
                    sixNumberList.add(sixNumbers)
                }
                if (i == 20) {
                    num1 = result17[18] + result17[15]
                    num2 = result28[15] + result28[12]
                    num3 = result39[12] + result39[9]
                    num4 = result410[9] + result410[26]
                    num5 = result511[26] + result612[33]
                    num6 = result612[33] + result511[26]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sixNumbers = SixNumber(num1, num2, num3, num4, num5, num6)
                    sixNumberList.add(sixNumbers)
                }
                if (i == 21) {
                    num1 = result17[25] + result17[26]
                    num2 = result28[26] + result28[26]
                    num3 = result39[26] + result39[12]
                    num4 = result410[12] + result410[18]
                    num5 = result511[18] + result612[24]
                    num6 = result612[24] + result511[23]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sixNumbers = SixNumber(num1, num2, num3, num4, num5, num6)
                    sixNumberList.add(sixNumbers)
                }
                if (i == 22) {
                    num1 = result17[27] + result17[34]
                    num2 = result28[14] + result28[21]
                    num3 = result39[21] + result39[26]
                    num4 = result410[26] + result410[34]
                    num5 = result511[34] + result511[26]
                    num6 = result612[26] + result612[26]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sixNumbers = SixNumber(num1, num2, num3, num4, num5, num6)
                    sixNumberList.add(sixNumbers)
                }
                if (i == 23) {
                    num1 = result17[26] + result17[34]
                    num2 = result28[34] + result28[26]
                    num3 = result39[26] + result39[21]
                    num4 = result410[21] + result410[14]
                    num5 = result511[14] + result612[27]
                    num6 = result612[27] + result511[26]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sixNumbers = SixNumber(num1, num2, num3, num4, num5, num6)
                    sixNumberList.add(sixNumbers)
                }
                if (i == 24) {
                    num1 = result17[8] + result17[16]
                    num2 = result28[16] + result28[24]
                    num3 = result39[24] + result39[8]
                    num4 = result410[8] + result410[33]
                    num5 = result511[33] + result612[12]
                    num6 = result612[12] + result511[11]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sixNumbers = SixNumber(num1, num2, num3, num4, num5, num6)
                    sixNumberList.add(sixNumbers)
                }
                if (i == 25) {
                    num1 = result17[9] + result17[18]
                    num2 = result28[18] + result28[14]
                    num3 = result39[14] + result39[16]
                    num4 = result410[16] + result410[18]
                    num5 = result511[18] + result612[36]
                    num6 = result612[36] + result511[14]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sixNumbers = SixNumber(num1, num2, num3, num4, num5, num6)
                    sixNumberList.add(sixNumbers)
                }
                if (i == 26) {
                    num1 = result17[33] + result17[36]
                    num2 = result28[36] + result28[21]
                    num3 = result39[21] + result39[22]
                    num4 = result410[22] + result410[23]
                    num5 = result511[23] + result612[24]
                    num6 = result612[24] + result511[26]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sixNumbers = SixNumber(num1, num2, num3, num4, num5, num6)
                    sixNumberList.add(sixNumbers)
                }
                if (i == 27) {
                    num1 = result17[11] + result17[36]
                    num2 = result28[22] + result28[33]
                    num3 = result39[33] + result39[26]
                    num4 = result410[26] + result410[9]
                    num5 = result511[9] + result612[12]
                    num6 = result612[12] + result511[33]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sixNumbers = SixNumber(num1, num2, num3, num4, num5, num6)
                    sixNumberList.add(sixNumbers)
                }
                if (i == 28) {
                    num1 = result17[12] + result17[9]
                    num2 = result28[9] + result28[26]
                    num3 = result39[26] + result39[33]
                    num4 = result410[33] + result410[22]
                    num5 = result511[22] + result612[26]
                    num6 = result612[11] + result511[21]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sixNumbers = SixNumber(num1, num2, num3, num4, num5, num6)
                    sixNumberList.add(sixNumbers)
                }
                if (i == 29) {
                    num1 = result17[12] + result17[24]
                    num2 = result28[24] + result28[15]
                    num3 = result39[15] + result39[18]
                    num4 = result410[18] + result410[21]
                    num5 = result511[21] + result612[24]
                    num6 = result612[24] + result511[26]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sixNumbers = SixNumber(num1, num2, num3, num4, num5, num6)
                    sixNumberList.add(sixNumbers)
                }
                if (i == 30) {
                    num1 = result17[13] + result17[12]
                    num2 = result28[12] + result28[11]
                    num3 = result39[11] + result39[33]
                    num4 = result410[33] + result410[11]
                    num5 = result511[11] + result612[21]
                    num6 = result612[12] + result511[26]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sixNumbers = SixNumber(num1, num2, num3, num4, num5, num6)
                    sixNumberList.add(sixNumbers)
                }
                if (i == 31) {
                    num1 = result17[26] + result17[25]
                    num2 = result28[25] + result28[34]
                    num3 = result39[34] + result39[33]
                    num4 = result410[33] + result410[26]
                    num5 = result511[26] + result612[26]
                    num6 = result612[35] + result511[34]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sixNumbers = SixNumber(num1, num2, num3, num4, num5, num6)
                    sixNumberList.add(sixNumbers)
                }
                if (i == 32) {
                    num1 = result17[8] + result17[27]
                    num2 = result28[27] + result28[33]
                    num3 = result39[33] + result39[9]
                    num4 = result410[9] + result410[21]
                    num5 = result511[12] + result612[21]
                    num6 = result612[11] + result511[26]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sixNumbers = SixNumber(num1, num2, num3, num4, num5, num6)
                    sixNumberList.add(sixNumbers)
                }
                if (i == 33) {
                    num1 = result17[14] + result17[13]
                    num2 = result28[13] + result28[16]
                    num3 = result39[16] + result39[15]
                    num4 = result410[15] + result410[18]
                    num5 = result511[18] + result612[27]
                    num6 = result612[17] + result511[26]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sixNumbers = SixNumber(num1, num2, num3, num4, num5, num6)
                    sixNumberList.add(sixNumbers)
                }
                if (i == 34) {
                    num1 = result17[19] + result17[18]
                    num2 = result28[18] + result28[26]
                    num3 = result39[21] + result39[26]
                    num4 = result410[36] + result410[22]
                    num5 = result511[22] + result612[12]
                    num6 = result612[21] + result511[26]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sixNumbers = SixNumber(num1, num2, num3, num4, num5, num6)
                    sixNumberList.add(sixNumbers)
                }
                if (i == 35) {
                    num1 = result17[24] + result17[23]
                    num2 = result28[23] + result28[9]
                    num3 = result39[9] + result39[25]
                    num4 = result410[33] + result410[11]
                    num5 = result511[11] + result612[26]
                    num6 = result612[12] + result511[21]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sixNumbers = SixNumber(num1, num2, num3, num4, num5, num6)
                    sixNumberList.add(sixNumbers)
                }
                if (i == 36) {
                    num1 = result17[25] + result17[34]
                    num2 = result28[34] + result28[8]
                    num3 = result39[8] + result39[21]
                    num4 = result410[12] + result410[16]
                    num5 = result511[16] + result612[26]
                    num6 = result612[36] + result511[26]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(2).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(2).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sixNumbers = SixNumber(num1, num2, num3, num4, num5, num6)
                    sixNumberList.add(sixNumbers)
                }
                if (i == 37) {
                    num1 = result17[24] + result17[35]
                    num2 = result28[35] + result28[15]
                    num3 = result39[15] + result39[36]
                    num4 = result410[36] + result410[11]
                    num5 = result511[11] + result612[21]
                    num6 = result612[12] + result511[26]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(2).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sixNumbers = SixNumber(num1, num2, num3, num4, num5, num6)
                    sixNumberList.add(sixNumbers)
                }
                if (i == 38) {
                    num1 = result17[8] + result17[11]
                    num2 = result28[11] + result28[34]
                    num3 = result39[14] + result39[17]
                    num4 = result410[17] + result410[36]
                    num5 = result511[36] + result612[33]
                    num6 = result612[23] + result511[26]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(1).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(1).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sixNumbers = SixNumber(num1, num2, num3, num4, num5, num6)
                    sixNumberList.add(sixNumbers)
                }
                if (i == 39) {
                    num1 = result17[9] + result17[13]
                    num2 = result28[13] + result28[17]
                    num3 = result39[17] + result39[21]
                    num4 = result410[21] + result410[11]
                    num5 = result511[11] + result612[21]
                    num6 = result612[12] + result511[23]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(1).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(1).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sixNumbers = SixNumber(num1, num2, num3, num4, num5, num6)
                    sixNumberList.add(sixNumbers)
                }
                if (i == 40) {
                    num1 = result17[34] + result17[27]
                    num2 = result28[27] + result28[11]
                    num3 = result39[11] + result39[15]
                    num4 = result410[15] + result410[19]
                    num5 = result511[19] + result612[23]
                    num6 = result612[23] + result511[19]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(10).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(10).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sixNumbers = SixNumber(num1, num2, num3, num4, num5, num6)
                    sixNumberList.add(sixNumbers)
                }
            }

            sixNumberAdapter = SixNumberAdapter(sixNumberList)
            binding.recyclerview6column.adapter = sixNumberAdapter
        }
    }

    private fun generate40sRowFor5() {
        if (binding.tvMaxNo.text.toString() == "-"
            || layoutColFiveBinding.tv15.text.toString() == "-" || layoutColFiveBinding.tv25.text.toString() == "-" || layoutColFiveBinding.tv35.text.toString() == "-"
            || layoutColFiveBinding.tv45.text.toString() == "-" || layoutColFiveBinding.tv55.text.toString() == "-" || layoutColFiveBinding.tv65.text.toString() == "-"
            || layoutColFiveBinding.tv75.text.toString() == "-" || layoutColFiveBinding.tv85.text.toString() == "-" || layoutColFiveBinding.tv95.text.toString() == "-"
            || layoutColFiveBinding.tv105.text.toString() == "-"
        ) {
            customToast("Result can't predict, please select number by clicking box.")
            return
        } else {
            var string1: String? = null
            var string2: String? = null
            var string3: String? = null
            var string4: String? = null
            var string5: String? = null
            var string6: String? = null
            var string7: String? = null
            var string8: String? = null
            var string9: String? = null
            var string10: String? = null

            if (layoutColFiveBinding.tv15.text.toString().toInt() in 1..9) {
                string1 = "11"
            } else {
                string1 = layoutColFiveBinding.tv15.text.toString()
            }
            if (layoutColFiveBinding.tv25.text.toString().toInt() in 1..9) {
                string2 = "13"
            } else {
                string2 = layoutColFiveBinding.tv25.text.toString()
            }
            if (layoutColFiveBinding.tv35.text.toString().toInt() in 1..9) {
                string3 = "15"
            } else {
                string3 = layoutColFiveBinding.tv35.text.toString()
            }
            if (layoutColFiveBinding.tv45.text.toString().toInt() in 1..9) {
                string4 = "17"
            } else {
                string4 = layoutColFiveBinding.tv45.text.toString()
            }
            if (layoutColFiveBinding.tv55.text.toString().toInt() in 1..9) {
                string5 = "19"
            } else {
                string5 = layoutColFiveBinding.tv55.text.toString()
            }
            if (layoutColFiveBinding.tv65.text.toString().toInt() in 1..9) {
                string6 = "21"
            } else {
                string6 = layoutColFiveBinding.tv65.text.toString()
            }
            if (layoutColFiveBinding.tv75.text.toString().toInt() in 1..9) {
                string7 = "23"
            } else {
                string7 = layoutColFiveBinding.tv75.text.toString()
            }
            if (layoutColFiveBinding.tv85.text.toString().toInt() in 1..9) {
                string8 = "25"
            } else {
                string8 = layoutColFiveBinding.tv85.text.toString()
            }
            if (layoutColFiveBinding.tv95.text.toString().toInt() in 1..9) {
                string9 = "27"
            } else {
                string9 = layoutColFiveBinding.tv95.text.toString()
            }
            if (layoutColFiveBinding.tv105.text.toString().toInt() in 1..9) {
                string10 = "29"
            } else {
                string10 = layoutColFiveBinding.tv105.text.toString()
            }

            sumCol16 = Integer.parseInt(string1).plus(Integer.parseInt(string7))
            sumCol27 = Integer.parseInt(string2).plus(Integer.parseInt(string8))
            sumCol38 = Integer.parseInt(string3).plus(Integer.parseInt(string9))
            sumCol49 = Integer.parseInt(string4).plus(Integer.parseInt(string10))
            sumCol510 = Integer.parseInt(string5).plus(Integer.parseInt(string6))

//            var sumDigits16: Int = getSumOfDigits(sumCol17!!)
//            var sumDigits27: Int = getSumOfDigits(sumCol28!!)
//            var sumDigits38: Int = getSumOfDigits(sumCol39!!)
//            var sumDigits49: Int = getSumOfDigits(sumCol410!!)
//            var sumDigits510: Int = getSumOfDigits(sumCol511!!)

            var sumDigits16: Int = getSumOfDigits(sumCol16!!)
            var sumDigits27: Int = getSumOfDigits(sumCol27!!)
            var sumDigits38: Int = getSumOfDigits(sumCol38!!)
            var sumDigits49: Int = getSumOfDigits(sumCol49!!)
            var sumDigits510: Int = getSumOfDigits(sumCol510!!)

            val powSumDigits16 = BigInteger(sumDigits16.toString()).pow(80)
            val powSumDigits27 = BigInteger(sumDigits27.toString()).pow(80)
            val powSumDigits38 = BigInteger(sumDigits38.toString()).pow(80)
            val powSumDigits49 = BigInteger(sumDigits49.toString()).pow(80)
            val powSumDigits510 = BigInteger(sumDigits510.toString()).pow(80)

            val result17 = powSumDigits16.toString().replace("0", "7").map { it.toString() }.toTypedArray()
            val result28 = powSumDigits27.toString().replace("0", "8").map { it.toString() }.toTypedArray()
            val result39 = powSumDigits38.toString().replace("0", "9").map { it.toString() }.toTypedArray()
            val result410 = powSumDigits49.toString().replace("0", "1").map { it.toString() }.toTypedArray()
            val result511 = powSumDigits510.toString().replace("0", "2").map { it.toString() }.toTypedArray()

            var num1: String? = null
            var num2: String? = null
            var num3: String? = null
            var num4: String? = null
            var num5: String? = null

            if (result17.size == 1 || result28.size == 1 || result39.size == 1 || result410.size == 1 || result511.size == 1) {
                customToast("Result can't predict, please select number by clicking box.")
                return
            }
            fiveNumberList.clear()

            for (i in 1..40) {
                if (i == 1) {
                    num1 = result17[25] + result17[26]
                    num2 = result28[26] + result28[33]
                    num3 = result39[33] + result39[34]
                    num4 = result410[34] + result410[35]
                    num5 = result511[35] + result511[26]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }

                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2)
                            .toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2)
                            .toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = binding.tvMaxNo.text.toString().toInt()!!.minus(1)
                            .toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }

                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().plus(4).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().plus(1).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().div(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().plus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().plus(5).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().plus(6).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().plus(7).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().plus(1).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().div(2).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().plus(4).toString()

                    var fiveNumbers = FiveNumber(num1, num2, num3, num4, num5)
                    fiveNumberList.add(fiveNumbers)
                }
                if (i == 2) {
                    num1 = result28[8] + result28[9]
                    num2 = result28[8] + result28[9]
                    num3 = result39[9] + result39[33]
                    num4 = result410[33] + result410[11]
                    num5 = result511[11] + result511[12]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }

                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2)
                            .toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = binding.tvMaxNo.text.toString().toInt()!!.minus(3)
                            .toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = binding.tvMaxNo.text.toString().toInt()!!.minus(2)
                            .toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()

                    var fiveNumbers = FiveNumber(num1, num2, num3, num4, num5)
                    fiveNumberList.add(fiveNumbers)
                }
                if (i == 3) {
                    num1 = result17[13] + result17[17]
                    num2 = result28[14] + result28[23]
                    num3 = result39[15] + result39[16]
                    num4 = result410[16] + result410[17]
                    num5 = result511[17] + result511[18]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()

                    var fiveNumbers = FiveNumber(num1, num2, num3, num4, num5)
                    fiveNumberList.add(fiveNumbers)
                }
                if (i == 4) {
                    num1 = result17[19] + result17[36]
                    num2 = result28[36] + result28[1]
                    num3 = result39[21] + result39[22]
                    num4 = result410[22] + result410[23]
                    num5 = result511[23] + result511[24]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }

                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()

                    var fiveNumbers = FiveNumber(num1, num2, num3, num4, num5)
                    fiveNumberList.add(fiveNumbers)
                }
                if (i == 5) {
                    num1 = result17[24] + result17[27]
                    num2 = result28[23] + result28[24]
                    num3 = result39[22] + result39[21]
                    num4 = result410[21] + result410[36]
                    num5 = result511[36] + result511[19]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()

                    var fiveNumbers = FiveNumber(num1, num2, num3, num4, num5)
                    fiveNumberList.add(fiveNumbers)
                }
                if (i == 6) {
                    num1 = result17[18] + result17[27]
                    num2 = result28[27] + result28[8]
                    num3 = result39[16] + result39[15]
                    num4 = result410[15] + result410[13]
                    num5 = result511[14] + result511[13]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    var fiveNumbers = FiveNumber(num1, num2, num3, num4, num5)
                    fiveNumberList.add(fiveNumbers)
                }
                if (i == 7) {
                    num1 = result17[12] + result17[7]
                    num2 = result28[11] + result28[33]
                    num3 = result39[33] + result39[19]
                    num4 = result410[25] + result410[28]
                    num5 = result511[18] + result511[27]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    var fiveNumbers = FiveNumber(num1, num2, num3, num4, num5)
                    fiveNumberList.add(fiveNumbers)
                }
                if (i == 8) {
                    num1 = result17[16] + result17[36]
                    num2 = result28[15] + result28[19]
                    num3 = result39[14] + result39[23]
                    num4 = result410[23] + result410[22]
                    num5 = result511[27] + result511[18]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    var fiveNumbers = FiveNumber(num1, num2, num3, num4, num5)
                    fiveNumberList.add(fiveNumbers)
                }
                if (i == 9) {
                    num1 = result17[11] + result17[13]
                    num2 = result28[18] + result28[15]
                    num3 = result39[15] + result39[17]
                    num4 = result410[27] + result410[9]
                    num5 = result511[9] + result511[11]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    var fiveNumbers = FiveNumber(num1, num2, num3, num4, num5)
                    fiveNumberList.add(fiveNumbers)
                }
                if (i == 10) {
                    num1 = result17[11] + result17[13]
                    num2 = result28[13] + result28[16]
                    num3 = result39[15] + result39[17]
                    num4 = result410[17] + result410[19]
                    num5 = result511[19] + result511[12]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    var fiveNumbers = FiveNumber(num1, num2, num3, num4, num5)
                    fiveNumberList.add(fiveNumbers)
                }
                if (i == 11) {
                    num1 = result17[21] + result17[28]
                    num2 = result28[23] + result28[27]
                    num3 = result39[25] + result39[33]
                    num4 = result410[33] + result410[35]
                    num5 = result511[35] + result511[27]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    var fiveNumbers = FiveNumber(num1, num2, num3, num4, num5)
                    fiveNumberList.add(fiveNumbers)
                }
                if (i == 12) {
                    num1 = result17[27] + result17[33]
                    num2 = result28[30] + result28[23]
                    num3 = result39[13] + result39[16]
                    num4 = result410[16] + result410[19]
                    num5 = result511[19] + result511[22]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    var fiveNumbers = FiveNumber(num1, num2, num3, num4, num5)
                    fiveNumberList.add(fiveNumbers)
                }
                if (i == 13) {
                    num1 = result17[21] + result17[24]
                    num2 = result28[26] + result28[25]
                    num3 = result39[25] + result39[26]
                    num4 = result410[26] + result410[33]
                    num5 = result511[33] + result511[34]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    var fiveNumbers = FiveNumber(num1, num2, num3, num4, num5)
                    fiveNumberList.add(fiveNumbers)
                }
                if (i == 14) {
                    num1 = result17[16] + result17[34]
                    num2 = result28[4] + result28[26]
                    num3 = result39[26] + result39[26]
                    num4 = result410[8] + result410[18]
                    num5 = result511[18] + result511[12]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()

                    var fiveNumbers = FiveNumber(num1, num2, num3, num4, num5)
                    fiveNumberList.add(fiveNumbers)
                }
                if (i == 15) {
                    num1 = result17[2] + result17[14]
                    num2 = result28[14] + result28[18]
                    num3 = result39[16] + result39[18]
                    num4 = result410[18] + result410[36]
                    num5 = result511[36] + result511[22]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    var fiveNumbers = FiveNumber(num1, num2, num3, num4, num5)
                    fiveNumberList.add(fiveNumbers)
                }
                if (i == 16) {
                    num1 = result17[24] + result17[18]
                    num2 = result28[28] + result28[12]
                    num3 = result39[12] + result39[26]
                    num4 = result410[26] + result410[26]
                    num5 = result511[26] + result511[25]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    var fiveNumbers = FiveNumber(num1, num2, num3, num4, num5)
                    fiveNumberList.add(fiveNumbers)
                }
                if (i == 17) {
                    num1 = result17[33] + result17[26]
                    num2 = result28[26] + result28[9]
                    num3 = result39[9] + result39[12]
                    num4 = result410[12] + result410[15]
                    num5 = result511[15] + result511[18]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()

                    var fiveNumbers = FiveNumber(num1, num2, num3, num4, num5)
                    fiveNumberList.add(fiveNumbers)
                }
                if (i == 18) {
                    num1 = result17[34] + result17[8]
                    num2 = result28[8] + result28[12]
                    num3 = result39[12] + result39[16]
                    num4 = result410[16] + result410[36]
                    num5 = result511[36] + result511[24]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()

                    var fiveNumbers = FiveNumber(num1, num2, num3, num4, num5)
                    fiveNumberList.add(fiveNumbers)
                }
                if (i == 19) {
                    num1 = result17[24] + result17[36]
                    num2 = result28[36] + result28[16]
                    num3 = result39[16] + result39[12]
                    num4 = result410[12] + result410[8]
                    num5 = result511[8] + result511[34]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()

                    var fiveNumbers = FiveNumber(num1, num2, num3, num4, num5)
                    fiveNumberList.add(fiveNumbers)
                }
                if (i == 20) {
                    num1 = result17[18] + result17[15]
                    num2 = result28[15] + result28[12]
                    num3 = result39[12] + result39[9]
                    num4 = result410[9] + result410[26]
                    num5 = result511[26] + result511[33]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()

                    var fiveNumbers = FiveNumber(num1, num2, num3, num4, num5)
                    fiveNumberList.add(fiveNumbers)
                }
                if (i == 21) {
                    num1 = result17[25] + result17[26]
                    num2 = result28[26] + result28[26]
                    num3 = result39[26] + result39[12]
                    num4 = result410[12] + result410[18]
                    num5 = result511[18] + result511[24]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()

                    var fiveNumbers = FiveNumber(num1, num2, num3, num4, num5)
                    fiveNumberList.add(fiveNumbers)
                }
                if (i == 22) {
                    num1 = result17[27] + result17[34]
                    num2 = result28[14] + result28[21]
                    num3 = result39[21] + result39[26]
                    num4 = result410[26] + result410[34]
                    num5 = result511[34] + result511[26]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    var fiveNumbers = FiveNumber(num1, num2, num3, num4, num5)
                    fiveNumberList.add(fiveNumbers)
                }
                if (i == 23) {
                    num1 = result17[26] + result17[34]
                    num2 = result28[34] + result28[26]
                    num3 = result39[26] + result39[21]
                    num4 = result410[21] + result410[14]
                    num5 = result511[14] + result511[27]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()

                    var fiveNumbers = FiveNumber(num1, num2, num3, num4, num5)
                    fiveNumberList.add(fiveNumbers)
                }
                if (i == 24) {
                    num1 = result17[8] + result17[16]
                    num2 = result28[16] + result28[24]
                    num3 = result39[24] + result39[8]
                    num4 = result410[8] + result410[33]
                    num5 = result511[33] + result511[12]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()

                    var fiveNumbers = FiveNumber(num1, num2, num3, num4, num5)
                    fiveNumberList.add(fiveNumbers)
                }
                if (i == 25) {
                    num1 = result17[9] + result17[18]
                    num2 = result28[18] + result28[14]
                    num3 = result39[14] + result39[16]
                    num4 = result410[16] + result410[18]
                    num5 = result511[18] + result511[36]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()

                    var fiveNumbers = FiveNumber(num1, num2, num3, num4, num5)
                    fiveNumberList.add(fiveNumbers)
                }
                if (i == 26) {
                    num1 = result17[33] + result17[36]
                    num2 = result28[36] + result28[21]
                    num3 = result39[21] + result39[22]
                    num4 = result410[22] + result410[23]
                    num5 = result511[23] + result511[24]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()

                    var fiveNumbers = FiveNumber(num1, num2, num3, num4, num5)
                    fiveNumberList.add(fiveNumbers)
                }
                if (i == 27) {
                    num1 = result17[11] + result17[36]
                    num2 = result28[22] + result28[33]
                    num3 = result39[33] + result39[26]
                    num4 = result410[26] + result410[9]
                    num5 = result511[9] + result511[12]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()

                    var fiveNumbers = FiveNumber(num1, num2, num3, num4, num5)
                    fiveNumberList.add(fiveNumbers)
                }
                if (i == 28) {
                    num1 = result17[12] + result17[9]
                    num2 = result28[9] + result28[26]
                    num3 = result39[26] + result39[33]
                    num4 = result410[33] + result410[22]
                    num5 = result511[22] + result511[26]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()

                    var fiveNumbers = FiveNumber(num1, num2, num3, num4, num5)
                    fiveNumberList.add(fiveNumbers)
                }
                if (i == 29) {
                    num1 = result17[12] + result17[24]
                    num2 = result28[24] + result28[15]
                    num3 = result39[15] + result39[18]
                    num4 = result410[18] + result410[21]
                    num5 = result511[21] + result511[24]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()

                    var fiveNumbers = FiveNumber(num1, num2, num3, num4, num5)
                    fiveNumberList.add(fiveNumbers)
                }
                if (i == 30) {
                    num1 = result17[13] + result17[12]
                    num2 = result28[12] + result28[11]
                    num3 = result39[11] + result39[33]
                    num4 = result410[33] + result410[11]
                    num5 = result511[11] + result511[21]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()

                    var fiveNumbers = FiveNumber(num1, num2, num3, num4, num5)
                    fiveNumberList.add(fiveNumbers)
                }
                if (i == 31) {
                    num1 = result17[26] + result17[25]
                    num2 = result28[25] + result28[34]
                    num3 = result39[34] + result39[33]
                    num4 = result410[33] + result410[26]
                    num5 = result511[26] + result511[26]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()

                    var fiveNumbers = FiveNumber(num1, num2, num3, num4, num5)
                    fiveNumberList.add(fiveNumbers)
                }
                if (i == 32) {
                    num1 = result17[8] + result17[27]
                    num2 = result28[27] + result28[33]
                    num3 = result39[33] + result39[9]
                    num4 = result410[9] + result410[21]
                    num5 = result511[12] + result511[21]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()

                    var fiveNumbers = FiveNumber(num1, num2, num3, num4, num5)
                    fiveNumberList.add(fiveNumbers)
                }
                if (i == 33) {
                    num1 = result17[14] + result17[13]
                    num2 = result28[13] + result28[16]
                    num3 = result39[16] + result39[15]
                    num4 = result410[15] + result410[18]
                    num5 = result511[18] + result511[27]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()

                    var fiveNumbers = FiveNumber(num1, num2, num3, num4, num5)
                    fiveNumberList.add(fiveNumbers)
                }
                if (i == 34) {
                    num1 = result17[19] + result17[18]
                    num2 = result28[18] + result28[26]
                    num3 = result39[21] + result39[26]
                    num4 = result410[36] + result410[22]
                    num5 = result511[22] + result511[12]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()

                    var fiveNumbers = FiveNumber(num1, num2, num3, num4, num5)
                    fiveNumberList.add(fiveNumbers)
                }
                if (i == 35) {
                    num1 = result17[24] + result17[23]
                    num2 = result28[23] + result28[9]
                    num3 = result39[9] + result39[25]
                    num4 = result410[33] + result410[11]
                    num5 = result511[11] + result511[26]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()

                    var fiveNumbers = FiveNumber(num1, num2, num3, num4, num5)
                    fiveNumberList.add(fiveNumbers)
                }
                if (i == 36) {
                    num1 = result17[25] + result17[34]
                    num2 = result28[34] + result28[8]
                    num3 = result39[8] + result39[21]
                    num4 = result410[12] + result410[16]
                    num5 = result511[16] + result511[26]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()

                    var fiveNumbers = FiveNumber(num1, num2, num3, num4, num5)
                    fiveNumberList.add(fiveNumbers)
                }
                if (i == 37) {
                    num1 = result17[24] + result17[35]
                    num2 = result28[35] + result28[15]
                    num3 = result39[15] + result39[36]
                    num4 = result410[36] + result410[11]
                    num5 = result511[11] + result511[21]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    var fiveNumbers = FiveNumber(num1, num2, num3, num4, num5)
                    fiveNumberList.add(fiveNumbers)
                }
                if (i == 38) {
                    num1 = result17[8] + result17[11]
                    num2 = result28[11] + result28[34]
                    num3 = result39[14] + result39[17]
                    num4 = result410[17] + result410[36]
                    num5 = result511[36] + result511[33]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()

                    var fiveNumbers = FiveNumber(num1, num2, num3, num4, num5)
                    fiveNumberList.add(fiveNumbers)
                }
                if (i == 39) {
                    num1 = result17[9] + result17[13]
                    num2 = result28[13] + result28[17]
                    num3 = result39[17] + result39[21]
                    num4 = result410[21] + result410[11]
                    num5 = result511[11] + result511[21]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()

                    var fiveNumbers = FiveNumber(num1, num2, num3, num4, num5)
                    fiveNumberList.add(fiveNumbers)
                }
                if (i == 40) {
                    num1 = result17[34] + result17[27]
                    num2 = result28[27] + result28[11]
                    num3 = result39[11] + result39[15]
                    num4 = result410[15] + result410[19]
                    num5 = result511[19] + result511[23]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()

                    var fiveNumbers = FiveNumber(num1, num2, num3, num4, num5)
                    fiveNumberList.add(fiveNumbers)
                }
            }

            fiveNumberAdapter = FiveNumberAdapter(fiveNumberList)
            binding.recyclerview6column.adapter = fiveNumberAdapter
        }
    }

    private fun generate40sRowFor7() {
        if (binding.tvMaxNo.text.toString() == "-"
            || layoutColSevenBinding.tv17.text.toString() == "-" || layoutColSevenBinding.tv27.text.toString() == "-" || layoutColSevenBinding.tv37.text.toString() == "-"
            || layoutColSevenBinding.tv47.text.toString() == "-" || layoutColSevenBinding.tv57.text.toString() == "-" || layoutColSevenBinding.tv67.text.toString() == "-"
            || layoutColSevenBinding.tv77.text.toString() == "-" || layoutColSevenBinding.tv87.text.toString() == "-" || layoutColSevenBinding.tv97.text.toString() == "-"
            || layoutColSevenBinding.tv107.text.toString() == "-" || layoutColSevenBinding.tv117.text.toString() == "-" || layoutColSevenBinding.tv127.text.toString() == "-"
            || layoutColSevenBinding.tv137.text.toString() == "-" || layoutColSevenBinding.tv147.text.toString() == "-"
        ) {
            customToast("Result can't predict, please select number by clicking box.")
            return
        } else {
            var string1: String? = null
            var string2: String? = null
            var string3: String? = null
            var string4: String? = null
            var string5: String? = null
            var string6: String? = null
            var string7: String? = null
            var string8: String? = null
            var string9: String? = null
            var string10: String? = null
            var string11: String? = null
            var string12: String? = null
            var string13: String? = null
            var string14: String? = null

            if (layoutColSevenBinding.tv17.text.toString().toInt() in 1..9) {
                string1 = "11"
            } else {
                string1 = layoutColSevenBinding.tv17.text.toString()
            }
            if (layoutColSevenBinding.tv27.text.toString().toInt() in 1..9) {
                string2 = "13"
            } else {
                string2 = layoutColSevenBinding.tv27.text.toString()
            }
            if (layoutColSevenBinding.tv37.text.toString().toInt() in 1..9) {
                string3 = "15"
            } else {
                string3 = layoutColSevenBinding.tv37.text.toString()
            }
            if (layoutColSevenBinding.tv47.text.toString().toInt() in 1..9) {
                string4 = "17"
            } else {
                string4 = layoutColSevenBinding.tv47.text.toString()
            }
            if (layoutColSevenBinding.tv57.text.toString().toInt() in 1..9) {
                string5 = "19"
            } else {
                string5 = layoutColSevenBinding.tv57.text.toString()
            }
            if (layoutColSevenBinding.tv67.text.toString().toInt() in 1..9) {
                string6 = "21"
            } else {
                string6 = layoutColSevenBinding.tv67.text.toString()
            }
            if (layoutColSevenBinding.tv77.text.toString().toInt() in 1..9) {
                string7 = "23"
            } else {
                string7 = layoutColSevenBinding.tv77.text.toString()
            }
            if (layoutColSevenBinding.tv87.text.toString().toInt() in 1..9) {
                string8 = "25"
            } else {
                string8 = layoutColSevenBinding.tv87.text.toString()
            }
            if (layoutColSevenBinding.tv97.text.toString().toInt() in 1..9) {
                string9 = "27"
            } else {
                string9 = layoutColSevenBinding.tv97.text.toString()
            }
            if (layoutColSevenBinding.tv107.text.toString().toInt() in 1..9) {
                string10 = "29"
            } else {
                string10 = layoutColSevenBinding.tv107.text.toString()
            }
            if (layoutColSevenBinding.tv117.text.toString().toInt() in 1..9) {
                string11 = "31"
            } else {
                string11 = layoutColSevenBinding.tv117.text.toString()
            }
            if (layoutColSevenBinding.tv127.text.toString().toInt() in 1..9) {
                string12 = "33"
            } else {
                string12 = layoutColSevenBinding.tv127.text.toString()
            }
            if (layoutColSevenBinding.tv137.text.toString().toInt() in 1..9) {
                string13 = "17"
            } else {
                string13 = layoutColSevenBinding.tv137.text.toString()
            }
            if (layoutColSevenBinding.tv147.text.toString().toInt() in 1..9) {
                string14 = "18"
            } else {
                string14 = layoutColSevenBinding.tv147.text.toString()
            }
            sumCol18 = Integer.parseInt(string1).plus(Integer.parseInt(string8))
            sumCol29 = Integer.parseInt(string2).plus(Integer.parseInt(string9))
            sumCol310 = Integer.parseInt(string3).plus(Integer.parseInt(string10))
            sumCol411 = Integer.parseInt(string4).plus(Integer.parseInt(string11))
            sumCol512 = Integer.parseInt(string5).plus(Integer.parseInt(string12))
            sumCol613 = Integer.parseInt(string6).plus(Integer.parseInt(string13))
            sumCol714 = Integer.parseInt(string7).plus(Integer.parseInt(string14))

            var sumDigits18: Int = getSumOfDigits(sumCol18!!)
            var sumDigits29: Int = getSumOfDigits(sumCol29!!)
            var sumDigits310: Int = getSumOfDigits(sumCol310!!)
            var sumDigits411: Int = getSumOfDigits(sumCol411!!)
            var sumDigits512: Int = getSumOfDigits(sumCol512!!)
            var sumDigits613: Int = getSumOfDigits(sumCol613!!)
            var sumDigits714: Int = getSumOfDigits(sumCol714!!)

            val powSumDigits18 = BigInteger(sumDigits18.toString()).pow(80)
            val powSumDigits29 = BigInteger(sumDigits29.toString()).pow(80)
            val powSumDigits310 = BigInteger(sumDigits310.toString()).pow(80)
            val powSumDigits411 = BigInteger(sumDigits411.toString()).pow(80)
            val powSumDigits512 = BigInteger(sumDigits512.toString()).pow(80)
            val powSumDigits613 = BigInteger(sumDigits613.toString()).pow(80)
            val powSumDigits714 = BigInteger(sumDigits714.toString()).pow(80)

            val result17 = powSumDigits18.toString().replace("0", "7").map { it.toString() }.toTypedArray()
            val result28 = powSumDigits29.toString().replace("0", "8").map { it.toString() }.toTypedArray()
            val result39 = powSumDigits310.toString().replace("0", "9").map { it.toString() }.toTypedArray()
            val result410 = powSumDigits411.toString().replace("0", "1").map { it.toString() }.toTypedArray()
            val result511 = powSumDigits512.toString().replace("0", "2").map { it.toString() }.toTypedArray()
            val result612 = powSumDigits613.toString().replace("0", "3").map { it.toString() }.toTypedArray()
            val result714 = powSumDigits714.toString().replace("0", "4").map { it.toString() }.toTypedArray()

            var num1: String? = null
            var num2: String? = null
            var num3: String? = null
            var num4: String? = null
            var num5: String? = null
            var num6: String? = null
            var num7: String? = null

            if (result17.size == 1 || result28.size == 1 || result39.size == 1 || result410.size == 1 || result511.size == 1 || result612.size == 1 || result714.size == 1) {
                customToast("Result can't predict, please select number by clicking box.")
                return
            }
            sevenNumberList.clear()

            for (i in 1..40) {
                if (i == 1) {
                    num1 = result17[25] + result17[26]
                    num2 = result28[26] + result28[33]
                    num3 = result39[33] + result39[34]
                    num4 = result410[34] + result410[35]
                    num5 = result511[35] + result511[26]
                    num6 = result612[26] + result612[25]
                    num7 = result714[1] + result714[2]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }

                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2)
                            .toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2)
                            .toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = binding.tvMaxNo.text.toString().toInt()!!.minus(1)
                            .toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().plus(4).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().plus(1).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().div(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().plus(3).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().plus(5).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().plus(6).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().plus(7).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().plus(8).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().plus(1).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().div(2).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().plus(3).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().plus(4).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().plus(5).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(6).toString()

                    var sevenNumbers = SevenNumber(num1, num2, num3, num4, num5, num6, num7)
                    sevenNumberList.add(sevenNumbers)
                }
                if (i == 2) {
                    num1 = result28[8] + result28[9]
                    num2 = result28[8] + result28[9]
                    num3 = result39[9] + result39[33]
                    num4 = result410[33] + result410[11]
                    num5 = result511[11] + result612[12]
                    num6 = result612[12] + result511[27]
                    num7 = result714[3] + result714[4]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }

                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2)
                            .toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = binding.tvMaxNo.text.toString().toInt()!!.minus(3)
                            .toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = binding.tvMaxNo.text.toString().toInt()!!.minus(2)
                            .toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sevenNumbers = SevenNumber(num1, num2, num3, num4, num5, num6, num7)
                    sevenNumberList.add(sevenNumbers)
                }
                if (i == 3) {
                    num1 = result17[13] + result17[17]
                    num2 = result28[14] + result28[23]
                    num3 = result39[15] + result39[16]
                    num4 = result410[16] + result410[17]
                    num5 = result511[17] + result612[18]
                    num6 = result612[18] + result511[19]
                    num7 = result714[5] + result714[6]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sevenNumbers = SevenNumber(num1, num2, num3, num4, num5, num6, num7)
                    sevenNumberList.add(sevenNumbers)
                }
                if (i == 4) {
                    num1 = result17[19] + result17[36]
                    num2 = result28[36] + result28[1]
                    num3 = result39[21] + result39[22]
                    num4 = result410[22] + result410[23]
                    num5 = result511[23] + result612[24]
                    num6 = result612[24] + result511[25]
                    num7 = result714[7] + result714[8]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sevenNumbers = SevenNumber(num1, num2, num3, num4, num5, num6, num7)
                    sevenNumberList.add(sevenNumbers)
                }
                if (i == 5) {
                    num1 = result17[24] + result17[27]
                    num2 = result28[23] + result28[24]
                    num3 = result39[22] + result39[21]
                    num4 = result410[21] + result410[36]
                    num5 = result511[36] + result612[19]
                    num6 = result612[19] + result511[26]
                    num7 = result714[9] + result714[10]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sevenNumbers = SevenNumber(num1, num2, num3, num4, num5, num6, num7)
                    sevenNumberList.add(sevenNumbers)
                }
                if (i == 6) {
                    num1 = result17[18] + result17[27]
                    num2 = result28[27] + result28[8]
                    num3 = result39[16] + result39[15]
                    num4 = result410[15] + result410[13]
                    num5 = result511[14] + result612[13]
                    num6 = result612[29] + result511[30]
                    num7 = result714[11] + result714[12]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sevenNumbers = SevenNumber(num1, num2, num3, num4, num5, num6, num7)
                    sevenNumberList.add(sevenNumbers)
                }
                if (i == 7) {
                    num1 = result17[12] + result17[7]
                    num2 = result28[11] + result28[33]
                    num3 = result39[33] + result39[19]
                    num4 = result410[25] + result410[28]
                    num5 = result511[18] + result612[27]
                    num6 = result612[17] + result511[28]
                    num7 = result714[13] + result714[14]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sevenNumbers = SevenNumber(num1, num2, num3, num4, num5, num6, num7)
                    sevenNumberList.add(sevenNumbers)
                }
                if (i == 8) {
                    num1 = result17[16] + result17[36]
                    num2 = result28[15] + result28[19]
                    num3 = result39[14] + result39[23]
                    num4 = result410[23] + result410[22]
                    num5 = result511[27] + result612[18]
                    num6 = result612[14] + result511[13]
                    num7 = result714[15] + result714[16]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sevenNumbers = SevenNumber(num1, num2, num3, num4, num5, num6, num7)
                    sevenNumberList.add(sevenNumbers)
                }
                if (i == 9) {
                    num1 = result17[11] + result17[13]
                    num2 = result28[18] + result28[15]
                    num3 = result39[15] + result39[17]
                    num4 = result410[27] + result410[9]
                    num5 = result511[9] + result612[11]
                    num6 = result612[11] + result511[27]
                    num7 = result714[17] + result714[18]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sevenNumbers = SevenNumber(num1, num2, num3, num4, num5, num6, num7)
                    sevenNumberList.add(sevenNumbers)
                }
                if (i == 10) {
                    num1 = result17[11] + result17[13]
                    num2 = result28[13] + result28[16]
                    num3 = result39[15] + result39[17]
                    num4 = result410[17] + result410[19]
                    num5 = result511[19] + result612[12]
                    num6 = result612[21] + result511[19]
                    num7 = result714[19] + result714[20]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sevenNumbers = SevenNumber(num1, num2, num3, num4, num5, num6, num7)
                    sevenNumberList.add(sevenNumbers)
                }
                if (i == 11) {
                    num1 = result17[21] + result17[28]
                    num2 = result28[23] + result28[27]
                    num3 = result39[25] + result39[33]
                    num4 = result410[33] + result410[35]
                    num5 = result511[35] + result612[27]
                    num6 = result612[27] + result511[35]
                    num7 = result714[21] + result714[22]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sevenNumbers = SevenNumber(num1, num2, num3, num4, num5, num6, num7)
                    sevenNumberList.add(sevenNumbers)
                }
                if (i == 12) {
                    num1 = result17[27] + result17[33]
                    num2 = result28[30] + result28[23]
                    num3 = result39[13] + result39[16]
                    num4 = result410[16] + result410[19]
                    num5 = result511[19] + result612[22]
                    num6 = result612[22] + result511[19]
                    num7 = result714[23] + result714[24]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sevenNumbers = SevenNumber(num1, num2, num3, num4, num5, num6, num7)
                    sevenNumberList.add(sevenNumbers)
                }
                if (i == 13) {
                    num1 = result17[21] + result17[24]
                    num2 = result28[26] + result28[25]
                    num3 = result39[25] + result39[26]
                    num4 = result410[26] + result410[33]
                    num5 = result511[33] + result612[34]
                    num6 = result612[34] + result511[25]
                    num7 = result714[25] + result714[26]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sevenNumbers = SevenNumber(num1, num2, num3, num4, num5, num6, num7)
                    sevenNumberList.add(sevenNumbers)
                }
                if (i == 14) {
                    num1 = result17[16] + result17[34]
                    num2 = result28[4] + result28[26]
                    num3 = result39[26] + result39[26]
                    num4 = result410[8] + result410[18]
                    num5 = result511[18] + result612[12]
                    num6 = result612[12] + result511[26]
                    num7 = result714[27] + result714[28]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sevenNumbers = SevenNumber(num1, num2, num3, num4, num5, num6, num7)
                    sevenNumberList.add(sevenNumbers)
                }
                if (i == 15) {
                    num1 = result17[2] + result17[14]
                    num2 = result28[14] + result28[18]
                    num3 = result39[16] + result39[18]
                    num4 = result410[18] + result410[36]
                    num5 = result511[36] + result612[22]
                    num6 = result612[22] + result511[21]
                    num7 = result714[29] + result714[30]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sevenNumbers = SevenNumber(num1, num2, num3, num4, num5, num6, num7)
                    sevenNumberList.add(sevenNumbers)
                }
                if (i == 16) {
                    num1 = result17[24] + result17[18]
                    num2 = result28[28] + result28[12]
                    num3 = result39[12] + result39[26]
                    num4 = result410[26] + result410[26]
                    num5 = result511[26] + result612[25]
                    num6 = result612[25] + result511[26]
                    num7 = result714[31] + result714[32]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sevenNumbers = SevenNumber(num1, num2, num3, num4, num5, num6, num7)
                    sevenNumberList.add(sevenNumbers)
                }
                if (i == 17) {
                    num1 = result17[33] + result17[26]
                    num2 = result28[26] + result28[9]
                    num3 = result39[9] + result39[12]
                    num4 = result410[12] + result410[15]
                    num5 = result511[15] + result612[18]
                    num6 = result612[18] + result511[17]
                    num7 = result714[33] + result714[34]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sevenNumbers = SevenNumber(num1, num2, num3, num4, num5, num6, num7)
                    sevenNumberList.add(sevenNumbers)
                }
                if (i == 18) {
                    num1 = result17[34] + result17[8]
                    num2 = result28[8] + result28[12]
                    num3 = result39[12] + result39[16]
                    num4 = result410[16] + result410[36]
                    num5 = result511[36] + result612[24]
                    num6 = result612[24] + result511[26]
                    num7 = result714[35] + result714[36]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sevenNumbers = SevenNumber(num1, num2, num3, num4, num5, num6, num7)
                    sevenNumberList.add(sevenNumbers)
                }
                if (i == 19) {
                    num1 = result17[24] + result17[36]
                    num2 = result28[36] + result28[16]
                    num3 = result39[16] + result39[12]
                    num4 = result410[12] + result410[8]
                    num5 = result511[8] + result612[34]
                    num6 = result612[34] + result511[8]
                    num7 = result714[1] + result714[3]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sevenNumbers = SevenNumber(num1, num2, num3, num4, num5, num6, num7)
                    sevenNumberList.add(sevenNumbers)
                }
                if (i == 20) {
                    num1 = result17[18] + result17[15]
                    num2 = result28[15] + result28[12]
                    num3 = result39[12] + result39[9]
                    num4 = result410[9] + result410[26]
                    num5 = result511[26] + result612[33]
                    num6 = result612[33] + result511[26]
                    num7 = result714[2] + result714[4]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sevenNumbers = SevenNumber(num1, num2, num3, num4, num5, num6, num7)
                    sevenNumberList.add(sevenNumbers)
                }
                if (i == 21) {
                    num1 = result17[25] + result17[26]
                    num2 = result28[26] + result28[26]
                    num3 = result39[26] + result39[12]
                    num4 = result410[12] + result410[18]
                    num5 = result511[18] + result612[24]
                    num6 = result612[24] + result511[23]
                    num7 = result714[5] + result714[7]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sevenNumbers = SevenNumber(num1, num2, num3, num4, num5, num6, num7)
                    sevenNumberList.add(sevenNumbers)
                }
                if (i == 22) {
                    num1 = result17[27] + result17[34]
                    num2 = result28[14] + result28[21]
                    num3 = result39[21] + result39[26]
                    num4 = result410[26] + result410[34]
                    num5 = result511[34] + result511[26]
                    num6 = result612[26] + result612[26]
                    num7 = result714[6] + result714[8]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sevenNumbers = SevenNumber(num1, num2, num3, num4, num5, num6, num7)
                    sevenNumberList.add(sevenNumbers)
                }
                if (i == 23) {
                    num1 = result17[26] + result17[34]
                    num2 = result28[34] + result28[26]
                    num3 = result39[26] + result39[21]
                    num4 = result410[21] + result410[14]
                    num5 = result511[14] + result612[27]
                    num6 = result612[27] + result511[26]
                    num7 = result714[7] + result714[9]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sevenNumbers = SevenNumber(num1, num2, num3, num4, num5, num6, num7)
                    sevenNumberList.add(sevenNumbers)
                }
                if (i == 24) {
                    num1 = result17[8] + result17[16]
                    num2 = result28[16] + result28[24]
                    num3 = result39[24] + result39[8]
                    num4 = result410[8] + result410[33]
                    num5 = result511[33] + result612[12]
                    num6 = result612[12] + result511[11]
                    num7 = result714[8] + result714[10]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sevenNumbers = SevenNumber(num1, num2, num3, num4, num5, num6, num7)
                    sevenNumberList.add(sevenNumbers)
                }
                if (i == 25) {
                    num1 = result17[9] + result17[18]
                    num2 = result28[18] + result28[14]
                    num3 = result39[14] + result39[16]
                    num4 = result410[16] + result410[18]
                    num5 = result511[18] + result612[36]
                    num6 = result612[36] + result511[14]
                    num7 = result714[9] + result714[11]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sevenNumbers = SevenNumber(num1, num2, num3, num4, num5, num6, num7)
                    sevenNumberList.add(sevenNumbers)
                }
                if (i == 26) {
                    num1 = result17[33] + result17[36]
                    num2 = result28[36] + result28[21]
                    num3 = result39[21] + result39[22]
                    num4 = result410[22] + result410[23]
                    num5 = result511[23] + result612[24]
                    num6 = result612[24] + result511[26]
                    num7 = result714[10] + result714[12]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sevenNumbers = SevenNumber(num1, num2, num3, num4, num5, num6, num7)
                    sevenNumberList.add(sevenNumbers)
                }
                if (i == 27) {
                    num1 = result17[11] + result17[36]
                    num2 = result28[22] + result28[33]
                    num3 = result39[33] + result39[26]
                    num4 = result410[26] + result410[9]
                    num5 = result511[9] + result612[12]
                    num6 = result612[12] + result511[33]
                    num7 = result714[11] + result714[13]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sevenNumbers = SevenNumber(num1, num2, num3, num4, num5, num6, num7)
                    sevenNumberList.add(sevenNumbers)
                }
                if (i == 28) {
                    num1 = result17[12] + result17[9]
                    num2 = result28[9] + result28[26]
                    num3 = result39[26] + result39[33]
                    num4 = result410[33] + result410[22]
                    num5 = result511[22] + result612[26]
                    num6 = result612[11] + result511[21]
                    num7 = result714[12] + result714[14]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sevenNumbers = SevenNumber(num1, num2, num3, num4, num5, num6, num7)
                    sevenNumberList.add(sevenNumbers)
                }
                if (i == 29) {
                    num1 = result17[12] + result17[24]
                    num2 = result28[24] + result28[15]
                    num3 = result39[15] + result39[18]
                    num4 = result410[18] + result410[21]
                    num5 = result511[21] + result612[24]
                    num6 = result612[24] + result511[26]
                    num7 = result714[13] + result714[15]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sevenNumbers = SevenNumber(num1, num2, num3, num4, num5, num6, num7)
                    sevenNumberList.add(sevenNumbers)
                }
                if (i == 30) {
                    num1 = result17[13] + result17[12]
                    num2 = result28[12] + result28[11]
                    num3 = result39[11] + result39[33]
                    num4 = result410[33] + result410[11]
                    num5 = result511[11] + result612[21]
                    num6 = result612[12] + result511[26]
                    num7 = result714[14] + result714[16]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sevenNumbers = SevenNumber(num1, num2, num3, num4, num5, num6, num7)
                    sevenNumberList.add(sevenNumbers)
                }
                if (i == 31) {
                    num1 = result17[26] + result17[25]
                    num2 = result28[25] + result28[34]
                    num3 = result39[34] + result39[33]
                    num4 = result410[33] + result410[26]
                    num5 = result511[26] + result612[26]
                    num6 = result612[35] + result511[34]
                    num7 = result714[15] + result714[17]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sevenNumbers = SevenNumber(num1, num2, num3, num4, num5, num6, num7)
                    sevenNumberList.add(sevenNumbers)
                }
                if (i == 32) {
                    num1 = result17[8] + result17[27]
                    num2 = result28[27] + result28[33]
                    num3 = result39[33] + result39[9]
                    num4 = result410[9] + result410[21]
                    num5 = result511[12] + result612[21]
                    num6 = result612[11] + result511[26]
                    num7 = result714[16] + result714[18]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sevenNumbers = SevenNumber(num1, num2, num3, num4, num5, num6, num7)
                    sevenNumberList.add(sevenNumbers)
                }
                if (i == 33) {
                    num1 = result17[14] + result17[13]
                    num2 = result28[13] + result28[16]
                    num3 = result39[16] + result39[15]
                    num4 = result410[15] + result410[18]
                    num5 = result511[18] + result612[27]
                    num6 = result612[17] + result511[26]
                    num7 = result714[17] + result714[19]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sevenNumbers = SevenNumber(num1, num2, num3, num4, num5, num6, num7)
                    sevenNumberList.add(sevenNumbers)
                }
                if (i == 34) {
                    num1 = result17[19] + result17[18]
                    num2 = result28[18] + result28[26]
                    num3 = result39[21] + result39[26]
                    num4 = result410[36] + result410[22]
                    num5 = result511[22] + result612[12]
                    num6 = result612[21] + result511[26]
                    num7 = result714[18] + result714[20]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sevenNumbers = SevenNumber(num1, num2, num3, num4, num5, num6, num7)
                    sevenNumberList.add(sevenNumbers)
                }
                if (i == 35) {
                    num1 = result17[24] + result17[23]
                    num2 = result28[23] + result28[9]
                    num3 = result39[9] + result39[25]
                    num4 = result410[33] + result410[11]
                    num5 = result511[11] + result612[26]
                    num6 = result612[12] + result511[21]
                    num7 = result714[19] + result714[21]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sevenNumbers = SevenNumber(num1, num2, num3, num4, num5, num6, num7)
                    sevenNumberList.add(sevenNumbers)
                }
                if (i == 36) {
                    num1 = result17[25] + result17[34]
                    num2 = result28[34] + result28[8]
                    num3 = result39[8] + result39[21]
                    num4 = result410[12] + result410[16]
                    num5 = result511[16] + result612[26]
                    num6 = result612[36] + result511[26]
                    num7 = result714[20] + result714[22]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(2).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(2).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sevenNumbers = SevenNumber(num1, num2, num3, num4, num5, num6, num7)
                    sevenNumberList.add(sevenNumbers)
                }
                if (i == 37) {
                    num1 = result17[24] + result17[35]
                    num2 = result28[35] + result28[15]
                    num3 = result39[15] + result39[36]
                    num4 = result410[36] + result410[11]
                    num5 = result511[11] + result612[21]
                    num6 = result612[12] + result511[26]
                    num7 = result714[21] + result714[23]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(2).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sevenNumbers = SevenNumber(num1, num2, num3, num4, num5, num6, num7)
                    sevenNumberList.add(sevenNumbers)
                }
                if (i == 38) {
                    num1 = result17[8] + result17[11]
                    num2 = result28[11] + result28[34]
                    num3 = result39[14] + result39[17]
                    num4 = result410[17] + result410[36]
                    num5 = result511[36] + result612[33]
                    num6 = result612[23] + result511[26]
                    num7 = result714[22] + result714[24]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(1).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(1).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sevenNumbers = SevenNumber(num1, num2, num3, num4, num5, num6, num7)
                    sevenNumberList.add(sevenNumbers)
                }
                if (i == 39) {
                    num1 = result17[9] + result17[13]
                    num2 = result28[13] + result28[17]
                    num3 = result39[17] + result39[21]
                    num4 = result410[21] + result410[11]
                    num5 = result511[11] + result612[21]
                    num6 = result612[12] + result511[23]
                    num7 = result714[23] + result714[25]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(1).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(1).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sevenNumbers = SevenNumber(num1, num2, num3, num4, num5, num6, num7)
                    sevenNumberList.add(sevenNumbers)
                }
                if (i == 40) {
                    num1 = result17[34] + result17[27]
                    num2 = result28[27] + result28[11]
                    num3 = result39[11] + result39[15]
                    num4 = result410[15] + result410[19]
                    num5 = result511[19] + result612[23]
                    num6 = result612[23] + result511[19]
                    num7 = result714[24] + result714[26]
                    if (num1.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num1 = num1.toInt().div(2).plus(1).toString()
                        if (num1.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num1 = num1.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num2.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num2 = num2.toInt().div(2).toString()
                        if (num2.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num2 = num2.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num3.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num3 = num3.toInt().div(2).toString()
                        if (num3.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num3 = num3.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num4.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num4 = num4.toInt().div(2).toString()
                        if (num4.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num4 = num4.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num5.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num5 = num5.toInt().div(2).toString()
                        if (num5.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num5 = num5.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num6.toInt() <= binding.tvMaxNo.text.toString().toInt()) {

                    } else {
                        num6 = num6.toInt().div(2).toString()
                        if (num6.toInt() > binding.tvMaxNo.text.toString().toInt()) {
                            num6 = num6.toInt().div(4).plus(1).toString()
                        }
                    }
                    if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(1).toString()
                    if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(3).toString()
                    if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(10).toString()

                    if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(1).toString()
                    if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(3).toString()
                    if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(10).toString()

                    if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(2).toString()
                    if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(1).toString()
                    if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(1).toString()

                    if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(2).toString()
                    if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(3).toString()

                    if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(4).toString()

                    var sevenNumbers = SevenNumber(num1, num2, num3, num4, num5, num6, num7)
                    sevenNumberList.add(sevenNumbers)
                }
            }

            sevenNumberAdapter = SevenNumberAdapter(sevenNumberList)
            binding.recyclerview6column.adapter = sevenNumberAdapter
        }
    }

    private fun initFBIntAdd() {

    }

    fun handlePurchases(purchases: List<Purchase>) {
        for (purchase in purchases) {
            if (!purchase.isAcknowledged) {
                val acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
                    .setPurchaseToken(purchase.purchaseToken)
                    .build()
                billingClient!!.acknowledgePurchase(acknowledgePurchaseParams, ackPurchase)
            }
        }
    }
    var ackPurchase = AcknowledgePurchaseResponseListener { billingResult ->
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
            GooglePlayBillingPreferences.setPurchased(true)
            Toast.makeText(applicationContext, "Item Purchased", Toast.LENGTH_SHORT).show()
            recreate()
        }
    }

}
