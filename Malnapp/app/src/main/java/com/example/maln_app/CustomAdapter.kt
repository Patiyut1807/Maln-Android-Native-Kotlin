import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.maln_app.ItemsViewModel
import com.example.maln_app.Manage_data
import com.example.maln_app.R

class CustomAdapter(private val mList: List<ItemsViewModel>,val context:Context) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.book_items, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        holder.bookName.text = ItemsViewModel.bname
        holder.genre.text = ItemsViewModel.genre
        holder.author.text = ItemsViewModel.author
        holder.publisher.text = ItemsViewModel.publisher
        holder.cardView.setOnClickListener {
        val manageData = Intent(context,Manage_data::class.java)
        manageData.putExtra("itemId",ItemsViewModel.id.toString())
        startActivity(context,manageData,null)

    }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val bookName: TextView = itemView.findViewById(R.id.book_name)
        val cardView: CardView = itemView.findViewById(R.id.card_view)
        val genre: TextView = itemView.findViewById(R.id.genre)
        val author: TextView = itemView.findViewById(R.id.author)
        val publisher: TextView = itemView.findViewById(R.id.publisher)
    }
}
