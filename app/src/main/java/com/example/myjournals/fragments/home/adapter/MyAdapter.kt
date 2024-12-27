import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myjournals.databinding.HeadingItemBinding
import com.example.myjournals.fragments.home.interfaces.IPromptListener
import com.example.myjournals.fragments.home.dataClass.Contact
import java.text.SimpleDateFormat
import java.util.Locale

class MyAdapter(private var iPromptDeleting: IPromptListener) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

//    var delete: DelAndUpdate? = null
//    private var update: DelAndUpdate? = null

    private lateinit var binding: HeadingItemBinding
    private var stringList: List<Contact> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = HeadingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return stringList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val contact = stringList[position]
        holder.binding.tvHeading.text = contact.name
        holder.binding.tvID.text = (position + 1).toString()
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        holder.binding.tvDate.text = dateFormat.format(contact.createDate)

        holder.binding.tvHeading.setOnClickListener{
            iPromptDeleting.onTextButtonClick(position,contact)
        }

        holder.binding.colorPicker.setOnClickListener {
            iPromptDeleting.onColorPickerButtonClick(position,contact)
        }

        holder.binding.icDel.setOnClickListener {
//            delete?.delete(contact.name)
            iPromptDeleting.onDeleteButtonClick(position, contact)
        }

        holder.binding.icEdit.setOnClickListener {
//            update?.update(contact.name)
            iPromptDeleting.onEditButtonClick(position, contact)
        }
    }

    fun setData(newList: List<Contact>) {
        this.stringList = newList
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding: HeadingItemBinding) : RecyclerView.ViewHolder(binding.root)

//    interface DelAndUpdate {
//        fun delete(position: String)
//        fun update(position: String)
//    }
}
