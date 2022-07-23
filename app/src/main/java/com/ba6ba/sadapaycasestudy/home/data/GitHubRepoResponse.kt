package com.ba6ba.sadapaycasestudy.home.data

import com.google.gson.annotations.SerializedName

data class GitHubRepoResponse(
    @SerializedName("total_count")
    val totalCount: Long?,
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean?,
    val items: List<RepositoryItem>? = emptyList()
)

data class RepositoryItem(
    val id: Int?,
    val name: String?,
    @SerializedName("full_name")
    val fullName: String?,
    @SerializedName("html_url")
    val repoUrl: String?,
    val description: String?,
    @SerializedName("stargazers_count")
    val stars: Int?,
    val language: String?,
    val watchers: Int?,
    val owner: RepositoryOwner? = null
)

data class RepositoryOwner(
    @SerializedName("avatar_url")
    val imageUrl: String?,
    @SerializedName("login")
    val userName: String?,
    @SerializedName("html_url")
    val profileUrl: String?
)