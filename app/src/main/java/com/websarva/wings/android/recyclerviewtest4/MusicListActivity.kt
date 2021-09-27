package com.websarva.wings.android.recyclerviewtest4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class MusicListActivity : AppCompatActivity() {
    companion object {
        val KEY_STATE = "playList"
    }

    val list = intent.getSerializableExtra(KEY_STATE) as ArrayList<Music>
    val musicList = list?.let { createMusicList(it) }
    private val adapter = RecyclerListAdapter(musicList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_list)


    }

    private fun createMusicList(list: ArrayList<Music>): MutableList<MutableMap<String, Any>> {
        val musicList: MutableList<MutableMap<String, Any>> = mutableListOf()

        list.forEach{
            var musicTitle = it.title as String
            var musicArtist = it.artist as String
            val music = mutableMapOf<String, Any>("title" to musicTitle, "artist" to musicArtist)
            musicList.add(music)
        }

        return musicList
    }

    private inner class RecyclerListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        //リスト1行分中でメニュー名を表示する画面部品
        var _musicNameRow: TextView
        //リスト1行分中でメニュー金額を表示する画面部品
        var _artistNameRow: TextView

        var _deleteButtonRow: ImageButton

        init {
            //引数で渡されたリスト1行分の画面部品から表示に使われるTextViewを取得
            _musicNameRow = itemView.findViewById(R.id.musicName)
            _artistNameRow = itemView.findViewById(R.id.artistName)
            _deleteButtonRow = itemView.findViewById(R.id.deleteButton)
        }
    }

    private inner class RecyclerListAdapter(private val _listData: MutableList<MutableMap<String, Any>>):RecyclerView.Adapter<RecyclerListViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):RecyclerListViewHolder{
            //レイアウトインフレータを取得
            val inflater = LayoutInflater.from(this@MusicListActivity)
            //row.xmlをインフレ―トし、1行分の画面部品とする
            val view = inflater.inflate(R.layout.musiclist_row, parent, false)

            //view.setOnClickListener(ItemClickListener())

            //ビューホルダオブジェクトを生成
            val holder = RecyclerListViewHolder(view)

            //生成したビューホルダをリターン
            return holder
        }

        override fun onBindViewHolder(holder: RecyclerListViewHolder, position: Int){
            //リストデータから該当1行分のデータを取得
            val item = _listData[position]
            //titleを取得
            val musicName = item["musicName"] as String
            //artistを取得
            val artistName = item["artist"] as String
            //メニュー名と金額をビューホルダ中のTextViewに設定
            holder._musicNameRow.text = musicName
            holder._artistNameRow.text = artistName

            holder._deleteButtonRow.setOnClickListener{
                _listData.removeAt(position)
                adapter.notifyDataSetChanged()
            }

        }

        override fun getItemCount(): Int {
            //リストデータ中の件数をリターン
            return _listData.size
        }
    }

    private inner class ItemClickListener : View.OnClickListener{
        override fun onClick(view: View) {
            val musicName = view.findViewById<TextView>(R.id.musicName)
            val music = musicName.text.toString()
            Toast.makeText(this@MusicListActivity, music, Toast.LENGTH_SHORT).show()
        }
    }
}