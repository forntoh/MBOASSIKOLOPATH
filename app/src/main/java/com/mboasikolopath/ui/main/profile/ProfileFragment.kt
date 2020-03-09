package com.mboasikolopath.ui.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mboasikolopath.R
import com.mboasikolopath.ui.base.ScopedFragment
import com.mboasikolopath.utilities.InsetDecoration
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class ProfileFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    private val viewModelFactory: ProfileViewModelFactory by instance()

    private val profileDataSection = Section()

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_profile, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ProfileViewModel::class.java)
        bindUI()
    }

    private fun bindUI() = launch {
        tv_user_name.text = auth.currentUser?.displayName

        auth.currentUser?.providerData?.forEach { userInfo ->
            when (userInfo.providerId) {
                "facebook.com" -> tv_sign_in_method.text = getString(R.string.signed_in_with_facebook)
                "google.com" -> tv_sign_in_method.text = getString(R.string.signed_in_with_google)
            }
            userInfo.photoUrl?.let {
                Glide.with(context!!).load(it).into(photoIv)
            }
        }


        val detailsAdapter = GroupAdapter<ViewHolder>().apply {
            add(profileDataSection)
        }
        rv_list.apply {
            adapter = detailsAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            addItemDecoration(InsetDecoration(16))
        }

        val icons = resources.obtainTypedArray(R.array.icons)

        profileDataSection.update(viewModel.getProfileData(icons))

        icons.recycle()
    }

}
