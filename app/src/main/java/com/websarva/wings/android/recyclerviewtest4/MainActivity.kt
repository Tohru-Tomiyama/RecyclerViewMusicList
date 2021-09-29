package com.websarva.wings.android.recyclerviewtest4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    val playList = createPlayList()
    private val adapter = RecyclerListAdapter(playList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //RecyclerViewを取得
        val recyclerview = findViewById<RecyclerView>(R.id.playList)
        //LinearLayoutManagerオブジェクトを生成
        val layout = LinearLayoutManager(this@MainActivity)
        //RecyclerViewにレイアウトマネージャーとしてLinearLayoutを設定
        recyclerview.layoutManager = layout
        //アダプタオブジェクトを生成
        //RecyclerViewにアダプタオブジェクトを設定
        recyclerview.adapter = adapter
        //区切り専用オブジェクトを生成
        val decorator = DividerItemDecoration(this@MainActivity, layout.orientation)
        //RecyclerViewに区切り線オブジェクトを設定
        recyclerview.addItemDecoration(decorator)

        val btClick = findViewById<Button>(R.id.addButton)

        btClick.setOnClickListener{
            var musicList: ArrayList<Music> = arrayListOf()
            val music = Music("New", "New")
            musicList.add(music)
            val newList = mutableMapOf<String, Any>("listName" to "new playlist", "musicList" to musicList)
            playList.add(newList)
            adapter.notifyDataSetChanged()
        }

    }


    private fun createPlayList(): MutableList<MutableMap<String, Any>> {
        var playList: MutableList<MutableMap<String, Any>> = mutableListOf()
        //val musicList = arrayListOf<Music>()
        var musicList: ArrayList<Music> = arrayListOf()
        var music = Music("群青", "YOASOBI")
        musicList.add(music)
        music = Music("irony", "ClariS")
        musicList.add(music)
        var list = mutableMapOf<String, Any>("listName" to "お気に入り", "musicList" to musicList)
        playList.add(list)

        return playList
    }

    private inner class RecyclerListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var _listNameRow: TextView

        var _deleteButtonRow: ImageButton

        init {
            //引数で渡されたリスト1行分の画面部品から表示に使われるTextViewを取得
            _listNameRow = itemView.findViewById(R.id.listTitle)
            _deleteButtonRow = itemView.findViewById(R.id.deleteListButton)
        }
    }

    private inner class RecyclerListAdapter(private val _listData: MutableList<MutableMap<String, Any>>):RecyclerView.Adapter<RecyclerListViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):RecyclerListViewHolder{
            //レイアウトインフレータを取得
            val inflater = LayoutInflater.from(this@MainActivity)
            //row.xmlをインフレ―トし、1行分の画面部品とする
            val view = inflater.inflate(R.layout.playlist_row, parent, false)

            //ビューホルダオブジェクトを生成
            val holder = RecyclerListViewHolder(view)

            //生成したビューホルダをリターン
            return holder
        }

        override fun onBindViewHolder(holder: RecyclerListViewHolder, position: Int){
            //リストデータから該当1行分のデータを取得
            val item = _listData[position]
            //メニュー名文字列を取得
            val listTitle = item["listName"] as String

            //ビューホルダ中のTextViewに設定
            holder._listNameRow.text = listTitle

            holder._deleteButtonRow.setOnClickListener{
                _listData.removeAt(position)
                adapter.notifyDataSetChanged()
            }

            holder._listNameRow.setOnClickListener{
                var listMap = playList[position] as MutableMap<String, Any>
                var list = listMap[listTitle] as ArrayList<Music>
                val intent2MusicList = Intent(this@MainActivity, MusicListActivity::class.java)
                startActivity(intent2MusicList)
                intent2MusicList.putExtra("playList", list)
            }

        }

        override fun getItemCount(): Int {
            //リストデータ中の件数をリターン
            return _listData.size
        }
    }
}