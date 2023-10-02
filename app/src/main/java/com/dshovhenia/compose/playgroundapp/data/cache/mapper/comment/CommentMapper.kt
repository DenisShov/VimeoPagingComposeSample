package com.dshovhenia.compose.playgroundapp.data.cache.mapper.comment

import com.dshovhenia.compose.playgroundapp.data.cache.mapper.Mapper
import com.dshovhenia.compose.playgroundapp.data.cache.mapper.user.UserMapper
import com.dshovhenia.compose.playgroundapp.data.cache.model.comment.CachedComment
import com.dshovhenia.compose.playgroundapp.data.model.comment.Comment
import javax.inject.Inject

class CommentMapper @Inject constructor(private val userMapper: UserMapper) :
    Mapper<CachedComment, Comment> {

    override fun mapFrom(type: CachedComment) = Comment(type.uri,
        type.text,
        type.created_on,
        type.nextPage,
        type.user?.let { userMapper.mapFrom(it) })

    override fun mapTo(type: Comment) = CachedComment(type.uri,
        type.text,
        type.createdOn,
        type.nextPage,
        type.user?.let { userMapper.mapTo(it) })
}
