package com.nexa.social

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.widget.*
import android.content.Context

class MainActivity : Activity() {
    private val bg = Color.rgb(16,17,27)
    private val surface = Color.rgb(27,29,43)
    private val purple = Color.rgb(139,92,246)
    private val pink = Color.rgb(236,72,153)
    private val white = Color.rgb(248,250,252)
    private val muted = Color.rgb(148,163,184)
    private lateinit var body: LinearLayout
    private val prefs by lazy { getSharedPreferences("nexa", Context.MODE_PRIVATE) }
    private val people = listOf(
        Triple("Layla", "UAE", "Art, coffee and thoughtful conversations"),
        Triple("Omar", "Egypt", "Photographer and traveler"),
        Triple("Sara", "Saudi Arabia", "Books, cooking and design"),
        Triple("Youssef", "Morocco", "Music producer and football fan"),
        Triple("Nour", "Jordan", "Designer exploring new places")
    )
    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); showWelcome() }

    private fun base(title: String) {
        val root = LinearLayout(this).apply { orientation=LinearLayout.VERTICAL; setBackgroundColor(bg); setPadding(20,24,20,12) }
        val heading = TextView(this).apply { text=title; textSize=27f; setTextColor(white); typeface=Typeface.DEFAULT_BOLD; setPadding(0,8,0,18) }
        root.addView(heading)
        val scroll = ScrollView(this)
        body = LinearLayout(this).apply { orientation=LinearLayout.VERTICAL }
        scroll.addView(body)
        root.addView(scroll, LinearLayout.LayoutParams(-1,0,1f))
        val nav = LinearLayout(this).apply { orientation=LinearLayout.HORIZONTAL; gravity=Gravity.CENTER; setPadding(0,12,0,0) }
        listOf("اكتشف","المحادثات","المحفظة","VIP","حسابي").forEach { label ->
            val b=Button(this).apply { text=label; textSize=10f; setTextColor(white); setBackgroundColor(surface); setOnClickListener {
                when(label) { "اكتشف"->showDiscovery(); "المحادثات"->showChats(); "المحفظة"->showWallet(); "VIP"->showVip(); else->showProfile() }
            } }
            nav.addView(b, LinearLayout.LayoutParams(0,52,1f))
        }
        root.addView(nav)
        setContentView(root)
    }
    private fun text(s:String, size:Float=15f, color:Int=white) = TextView(this).apply { text=s; textSize=size; setTextColor(color); setPadding(0,7,0,7) }
    private fun card(title:String, detail:String) {
        val box=LinearLayout(this).apply { orientation=LinearLayout.VERTICAL; setPadding(16,12,16,12); setBackgroundColor(surface) }
        box.addView(text(title,19f)); box.addView(text(detail,13f,muted))
        val lp=LinearLayout.LayoutParams(-1,-2); lp.bottomMargin=12; body.addView(box,lp)
    }
    private fun button(label:String, action:()->Unit) {
        val b=Button(this).apply { text=label; setTextColor(white); setBackgroundColor(purple); setOnClickListener { action() } }
        val lp=LinearLayout.LayoutParams(-1,-2); lp.bottomMargin=9; body.addView(b,lp)
    }
    private fun field(hint:String, value:String=""): EditText {
        val e=EditText(this).apply { this.hint=hint; setText(value); setTextColor(white); setHintTextColor(muted); setSingleLine(false) }
        body.addView(e); return e
    }
    private fun showWelcome() {
        base("NEXA")
        body.gravity=Gravity.CENTER
        body.addView(text("اكتشف • تواصل • تعرّف",22f))
        body.addView(text("نسخة تجريبية محلية — جميع الملفات الشخصية افتراضية",13f,muted))
        button("ابدأ كضيف") { showProfileSetup() }
    }
    private fun showProfileSetup() {
        base("إنشاء ملفك")
        val name=field("الاسم المستعار",prefs.getString("name","") ?: "")
        val country=field("الدولة",prefs.getString("country","الجزائر") ?: "الجزائر")
        val bio=field("نبذة قصيرة",prefs.getString("bio","") ?: "")
        button("حفظ ومتابعة") {
            prefs.edit().putString("name",name.text.toString().ifBlank{"Guest"}).putString("country",country.text.toString()).putString("bio",bio.text.toString()).putBoolean("ready",true).apply()
            showDiscovery()
        }
    }
    private fun showDiscovery() {
        base("اكتشف أشخاصًا")
        card("مساحة تجريبية","هذه الحسابات خيالية ومحلية وليست مستخدمين حقيقيين.")
        people.forEach { p ->
            card("●  ${p.first}  ·  ${p.second}",p.third)
            button("عرض الملف / بدء محادثة مع ${p.first}") { showChat(p.first) }
        }
    }
    private fun showChat(name:String) {
        base("محادثة مع $name")
        card("محادثة محلية تجريبية","لا يوجد اتصال بمستخدم آخر. الرسائل تحفظ على هذا الجهاز.")
        val history=prefs.getString("chat_$name","") ?: ""
        val log=text(history,14f,white); body.addView(log)
        val input=field("اكتب رسالة…")
        button("إرسال") {
            val msg=input.text.toString().trim()
            if(msg.isNotEmpty()) { val updated=(prefs.getString("chat_$name","") ?: "") + "\nأنت: $msg"; prefs.edit().putString("chat_$name",updated).apply(); showChat(name) }
        }
    }
    private fun showChats() {
        base("المحادثات")
        people.forEach { p -> button("محادثة ${p.first}") { showChat(p.first) } }
    }
    private fun showWallet() {
        base("المحفظة")
        card("500 Nexa Coins","رصيد تجريبي فقط — لا قيمة مالية ولا عمليات دفع.")
        listOf("هدية وردة · 10","هدية نجمة · 50","هدية تاج · 100").forEach { item -> button(item) { Toast.makeText(this,"ميزة تجريبية فقط",Toast.LENGTH_SHORT).show() } }
    }
    private fun showVip() {
        base("عضوية VIP")
        listOf("Gold","Platinum","Diamond").forEach { tier -> card(tier,"مزايا واجهة تجريبية — لا اشتراك ولا دفع حقيقي."); button("تجربة $tier") { Toast.makeText(this,"Demo only",Toast.LENGTH_SHORT).show() } }
    }
    private fun showProfile() {
        base("حسابي")
        val name= prefs.getString("name","Guest") ?: "Guest"
        card(name,prefs.getString("country","الجزائر") ?: "الجزائر")
        button("تعديل الملف") { showProfileSetup() }
        button("إعدادات وخصوصية") { showSettings() }
    }
    private fun showSettings() {
        base("الإعدادات")
        card("الخصوصية","لا توجد حسابات سحابية أو تحليلات أو رفع تلقائي للصور.")
        button("مسح البيانات المحلية") { prefs.edit().clear().apply(); Toast.makeText(this,"تم مسح بيانات التطبيق",Toast.LENGTH_SHORT).show(); showWelcome() }
        button("معاينة الاتصال المرئي") { Toast.makeText(this,"هذه النسخة لا تتصل بمشارك بعيد ولا تشغّل الكاميرا بعد",Toast.LENGTH_LONG).show() }
    }
}
