import request from '../utils/request'


// 列表
export function getList(pageNo, size, tab) {
  return request(({
    url: '/post/list',
    method: 'get',
    params: { pageNo: pageNo, size: size, tab: tab }
  }))
}

// 发布
export function post(topic) {
  let token = localStorage.getItem('token');
  return request({
    url: '/post/create',
    method: 'post',
    data: topic,
    headers: {
      'content-type': 'application/json',
      /* "Authorization":'Bearer ' + token  //token换成从缓存获取*/
      /*   "userName": 'Bearer ' + token,*/
      "Authorization":'Bearer ' + token
    }
  })
}

// 浏览
export function getTopic(id) {
  let username = localStorage.getItem('username');
  return request({
    url: `/post`,
    method: 'get',
    params: {
      id: id,
      username: username
    }
  })
}
// 获取详情页推荐
export function getRecommendTopics(id) {
  return request({
    url: '/post/recommend',
    method: 'get',
    params: {
      topicId: id
    }
  })
}

export function updateUsername(ruleForm) {
  let token = localStorage.getItem('token');
  return request({
    url: '/user/update',
    method: 'post',
    data: ruleForm,
    headers: {
      'content-type': 'application/json',
      "Authorization":'Bearer ' + token
    }
  })
}

export function updateAvatar(file) {
  let token = localStorage.getItem('token');
  return request({
    url: '/user/upload',
    method: 'post',
    data: file,
    headers: {
      'content-type': 'application/json',
      "Authorization":'Bearer ' + token
    }
  })
}

export function update(topic) {
  return request({
    url: '/post/update',
    method: 'post',
    data: topic
  })
}
//点赞
export function setLike(id) {
  let token = localStorage.getItem('token');
  return request({
    url: `/post/setlike/${id}`,
    method: 'get',
    headers: {
      'content-type': 'application/json',
      "Authorization":'Bearer ' + token
    }
  })
}

export function deleteTopic(id) {
  let token = localStorage.getItem('token');
  return request({
    url: `/post/delete/${id}`,
    method: 'delete',
    headers: {
      'content-type': 'application/json',
      "Authorization":'Bearer ' + token
    }
  })
}

export function cancelLike(id) {
  let token = localStorage.getItem('token');
  return request({
    url: `/post/cancelLike/${id}`,
    method: 'get',
    headers: {
      'content-type': 'application/json',
      "Authorization":'Bearer ' + token
    }
  })
}

export function open(message){
  window.alert(message, '', {
    confirmButtonText: '确定',
    callback: action => {
      this.$message({
        type: 'info',
        message: `action: ${ action }`
      });

    }
  });
}
export function getInfoByName(){
  let token = localStorage.getItem('token');
  return request({
    url:'/user/getuserinfo',
    method:'get',
    headers: {
      'content-type': 'application/json',
      "Authorization":'Bearer ' + token
    },
  })
}
export function sendcode(code){
  let token = localStorage.getItem('token');
  return request({
    url:'/verify',
    method:'post',
    headers: {
      'content-type': 'application/json',
      "Authorization":'Bearer ' + token
    },
    params:{
      code:code,

    }
  })
}
export function searchByKeyword(query) {
  return request({
    url: `/search`,
    method: 'get',
    params: {
      keyword: query.keyword,
      pageNum: query.pageNum,
      pageSize: query.pageSize
    }
  })
}
export function getimg(username) {
  return request({
    url: `/user/getimgs`,
    method: 'get',
    params: {
      username: username,

    }
  })
}

export function commentDel(Id) {
  let token = localStorage.getItem('token');
  return request({
    url: `/comment/delete_comment`,
    method: 'post',
    headers: {
      'content-type': 'application/json',
      "Authorization":'Bearer ' + token
    },
    params: {
      comment_id: Id,
    }
  })
}

export function uploadimg(file){
  let token = localStorage.getItem('token');
  return request({
    url:'/user/upload',
    method:'post',
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  }).then((data) => {

  })
}
export function myHistoryPost(pageNum,pageSize) {
  let token = localStorage.getItem('token');
  return request({
    url: `/user/getHistory`,
    method: 'get',
    params: {
      pageNum: pageNum,
      pageSize: pageSize
    },
    headers: {
      'content-type': 'application/json',
      "Authorization":'Bearer ' + token
    },
  })
}

export function getLikedPost(pageNum,pageSize) {
  let token = localStorage.getItem('token');
  return request({
    url: `/user/getLikedPost`,
    method: 'get',
    params: {
      pageNum: pageNum,
      pageSize: pageSize
    },
    headers: {
      'content-type': 'application/json',
      "Authorization":'Bearer ' + token
    },
  })
}
export function getTopicsByTag(paramMap) {
  return request({
    url: '/tag/' + paramMap.name,
    method: 'get',
    params: {
      page: paramMap.page,
      size: paramMap.size
    }
  })
}
