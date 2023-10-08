import request from '../utils/request'

export function fetchCommentsByTopicId(topic_Id) {
    return request({
        url: '/comment/get_comments',
        method: 'get',
        params: {
            topicid: topic_Id
        }
    })
}
export function post(topic) {
    return request({
        url: '/post/create',
        method: 'post',
        data: topic,

    })
}
export function pushComment(data) {
    let token = localStorage.getItem('token');
    return request({
        url: '/comment/add_comment',
        method: 'post',
        data: data,
        headers: {
            'content-type': 'application/json',
            /* "Authorization":'Bearer ' + token  //token换成从缓存获取*/
            /*   "userName": 'Bearer ' + token,*/
            "Authorization":'Bearer ' + token
        }
    })
}