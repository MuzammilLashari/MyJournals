import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myjournals.databinding.HeadingItemBinding
import com.example.myjournals.fragments.subHeadingFirst.dataClass.SubHeadingFirstDataClass
import com.example.myjournals.fragments.subHeadingFirst.interfaces.ISubHeadingListener
import java.text.SimpleDateFormat
import java.util.Locale

class SubHeadingFirstAdapter(private var iSubHeadingListener: ISubHeadingListener) :
    RecyclerView.Adapter<SubHeadingFirstAdapter.MyViewHolder>() {

//    var delete: DelAndUpdate? = null
//    private var update: DelAndUpdate? = null

    private lateinit var binding: HeadingItemBinding
    private var stringList: List<SubHeadingFirstDataClass> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = HeadingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val subheadingList = stringList[position]
        holder.binding.tvHeading.text = subheadingList.name
        holder.binding.tvID.text = (position + 1).toString()
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        holder.binding.tvDate.text = dateFormat.format(subheadingList.createDate)

        holder.binding.tvHeading.setOnClickListener{
            iSubHeadingListener.onTextButtonClick(position,subheadingList)
        }

        holder.binding.colorPicker.setOnClickListener {
            iSubHeadingListener.onColorPickerButtonClick(position,subheadingList)
        }

        holder.binding.icDel.setOnClickListener {
//            delete?.delete(subheadingList.name)
            iSubHeadingListener.onDeleteButtonClick(position, subheadingList)
        }

        holder.binding.icEdit.setOnClickListener {
//            update?.update(subheadingList.name)
            iSubHeadingListener.onEditButtonClick(position, subheadingList)
        }
    }


    override fun getItemCount(): Int {
        return stringList.size
    }

    fun setData(newList: List<SubHeadingFirstDataClass>) {
        this.stringList = newList
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding: HeadingItemBinding) : RecyclerView.ViewHolder(binding.root)

//    interface DelAndUpdate {
//        fun delete(position: String)
//        fun update(position: String)
//    }
}
