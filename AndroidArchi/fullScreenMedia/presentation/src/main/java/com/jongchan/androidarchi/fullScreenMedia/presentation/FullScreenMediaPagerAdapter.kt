package com.jongchan.androidarchi.fullScreenMedia.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import com.jongchan.androidarchi.common.entity.media.MediaItemVO
import com.jongchan.androidarchi.fullScreenMedia.presentation.databinding.ItemFullScreenMediaBinding

/**
 * ViewPager2 용 페이지 어댑터.
 *
 * 한 페이지는 이미지만 담는다(스와이프 대상). 뒤로가기/타이틀/하트 오버레이는 페이저 밖
 * [FullScreenMediaFragment] 가 고정 관리하므로 어댑터는 즐겨찾기 상태를 알 필요가 없다.
 */
internal class FullScreenMediaPagerAdapter(
    val onImageLoadStarted: () -> Unit,
    val onImageLoadCompleted: () -> Unit
) :
    RecyclerView.Adapter<FullScreenMediaPagerAdapter.MediaViewHolder>() {

    private var items: List<MediaItemVO> = emptyList()

    fun submit(newItems: List<MediaItemVO>) {
        if (items == newItems) return
        items = newItems
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        val binding = ItemFullScreenMediaBinding.inflate(
            LayoutInflater.from(parent.context), parent, false,
        )
        return MediaViewHolder(binding, onImageLoadStarted, onImageLoadCompleted)
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class MediaViewHolder(
        private val binding: ItemFullScreenMediaBinding,
        private val onImageLoadStarted: () -> Unit,
        private val onImageLoadCompleted: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MediaItemVO) {
            onImageLoadStarted()
            binding.mediaImage.load(item.contentsImageUrl) {
                listener(
                    onSuccess = { _, _ ->
                        onImageLoadCompleted()
                    },
                )
            }
            binding.mediaImage.contentDescription = item.title
        }
    }
}
