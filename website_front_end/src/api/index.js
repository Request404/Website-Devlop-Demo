import {request} from './request'

function Api() {

}

let api = new Api();

/** ========================DBooks============================ **/

Api.prototype.dBookAdd = function (dBooks) {
  return request({
    method: 'put',
    url: '/d-books/add',
    data: {
      name: dBooks.name,
      title: dBooks.title,
      tages: dBooks.tages,
      author: dBooks.author,
      country: dBooks.country,
      releaseDate: dBooks.releaseDate.format("yyyy-MM-dd"),
      introduction: dBooks.introduction,
      coverUrl: dBooks.coverUrl,
      publisher: dBooks.publisher,
      isbn: dBooks.isbn,
      price: dBooks.price,
      priceSource: dBooks.priceSource,
      pages: dBooks.pages
    }
  })
};

Api.prototype.dBookRemove = function (bookId) {
  return request({
    method: 'delete',
    url: '/d-books/delete',
    params: {
      bookId
    }
  })
};

Api.prototype.dBookGetAllBooks = function (pageNumber, pageSize, condition) {
  return request({
    method: 'get',
    url: '/d-books/getAllBooks',
    params: {
      pageNumber,
      pageSize,
      condition
    }
  })
};

Api.prototype.dBookGetOff = function (bookId) {
  return request({
    method: 'delete',
    url: '/d-books/off',
    params: {
      bookId
    }
  })
};

Api.prototype.dBookUpdate = function (dBooks) {
  return request({
    method: 'post',
    url: '/d-books/update',
    data: {
      id: dBooks.id,
      name: dBooks.name,
      title: dBooks.title,
      tages: dBooks.tages,
      author: dBooks.author,
      country: dBooks.country,
      releaseDate: dBooks.releaseDate.format("yyyy-MM-dd"),
      introduction: dBooks.introduction,
      coverUrl: dBooks.coverUrl,
      publisher: dBooks.publisher,
      isbn: dBooks.isbn,
      price: dBooks.price,
      priceSource: dBooks.priceSource,
      pages: dBooks.pages
    }
  })
};

Api.prototype.dBookClientGetAllBooks = function (pageNumber, pageSize, condition) {
  return request({
    method: 'get',
    url: '/d-books/website/open/clientGetAllBooks',
    params: {
      pageNumber,
      pageSize,
      condition
    }
  })
}

Api.prototype.dBookGetInfo = function (bookId) {
  return request({
    method: 'get',
    url: '/d-books/website/open/getInfo',
    params: {
      bookId
    }
  })
}

Api.prototype.dBookGetWebsiteIndexBooks = function (needCover) {
  return request({
    method: 'get',
    url: '/d-books/website/open/getWebsiteIndexBooks',
    params: {
      needCover
    }
  })
}

/** ========================DMovies============================ **/

Api.prototype.dMovieAdd = function (dMovies) {
  return request({
    method: 'put',
    url: '/d-movies/add',
    data: {
      name: dMovies.name,
      type: dMovies.type,
      country: dMovies.country,
      releaseDate: dMovies.releaseDate.format("yyyy-MM-dd"),
      director: dMovies.director,
      writer: dMovies.writer,
      actors: dMovies.actors,
      alternateName: dMovies.alternateName,
      minutes: dMovies.minutes,
      introduction: dMovies.introduction,
      coverUrl: dMovies.coverUrl
    }
  })
};

Api.prototype.dMovieRemove = function (MovieId) {
  return request({
    method: 'delete',
    url: '/d-movies/delete',
    params: {
      MovieId
    }
  })
};

Api.prototype.dMovieGetAllMovies = function (pageNumber, pageSize, condition) {
  return request({
    method: 'get',
    url: '/d-movies/getAllMovies',
    params: {
      pageNumber,
      pageSize,
      condition
    }
  })
};

Api.prototype.dMovieGetOff = function (MovieId) {
  return request({
    method: 'delete',
    url: '/d-movies/off',
    params: {
      MovieId
    }
  })
};

Api.prototype.dMovieUpdate = function (dMovies) {
  return request({
    method: 'post',
    url: '/d-movies/update',
    data: {
      id: dMovies.id,
      name: dMovies.name,
      type: dMovies.type,
      country: dMovies.country,
      releaseDate: dMovies.releaseDate.format("yyyy-MM-dd"),
      director: dMovies.director,
      writer: dMovies.writer,
      actors: dMovies.actors,
      alternateName: dMovies.alternateName,
      minutes: dMovies.minutes,
      introduction: dMovies.introduction,
      coverUrl: dMovies.coverUrl
    }
  })
};

Api.prototype.dMovieClientGetAllMovies = function (pageNumber, pageSize, condition) {
  return request({
    method: 'get',
    url: '/d-movies/website/open/clientGetAllMovies',
    params: {
      pageNumber,
      pageSize,
      condition
    }
  })
}

Api.prototype.dMovieGetInfo = function (MovieId) {
  return request({
    method: 'get',
    url: '/d-movies/website/open/getInfo',
    params: {
      MovieId
    }
  })
}

Api.prototype.dMovieGetWebsiteIndexMovies = function (needCover) {
  return request({
    method: 'get',
    url: '/d-movies/website/open/getWebsiteIndexMovies',
    params: {
      needCover
    }
  })
}

/** ========================DMusics============================ **/

Api.prototype.dMusicAdd = function (dMusics) {
  return request({
    method: 'put',
    url: '/d-musics/add',
    data: {
      name: dMusics.name,
      album: dMusics.album,
      singer: dMusics.singer,
      writeWords: dMusics.writeWords,
      compose: dMusics.compose,
      lyric: dMusics.lyric,
      type: dMusics.type,
      country: dMusics.country,
      minutes: dMusics.minutes,
      releaseYear: dMusics.releaseYear,
      albumAvatarUrl: dMusics.albumAvatarUrl,
      singerAvatarUrl: dMusics.singerAvatarUrl
    }
  })
};

Api.prototype.dMusicRemove = function (MusicId) {
  return request({
    method: 'delete',
    url: '/d-musics/delete',
    params: {
      MusicId
    }
  })
};

Api.prototype.dMusicGetAllMusics = function (pageNumber, pageSize, condition) {
  return request({
    method: 'get',
    url: '/d-musics/getAllMusics',
    params: {
      pageNumber,
      pageSize,
      condition
    }
  })
};

Api.prototype.dMusicGetOff = function (MusicId) {
  return request({
    method: 'delete',
    url: '/d-musics/off',
    params: {
      MusicId
    }
  })
};

Api.prototype.dMusicUpdate = function (dMusics) {
  return request({
    method: 'post',
    url: '/d-musics/update',
    data: {
      id: dMusics.id,
      name: dMusics.name,
      album: dMusics.album,
      singer: dMusics.singer,
      writeWords: dMusics.writeWords,
      compose: dMusics.compose,
      lyric: dMusics.lyric,
      type: dMusics.type,
      country: dMusics.country,
      minutes: dMusics.minutes,
      releaseYear: dMusics.releaseYear,
      albumAvatarUrl: dMusics.albumAvatarUrl,
      singerAvatarUrl: dMusics.singerAvatarUrl
    }
  })
};

Api.prototype.dMusicClientGetAllMusics = function (pageNumber, pageSize, condition) {
  return request({
    method: 'get',
    url: '/d-musics/website/open/clientGetAllMusics',
    params: {
      pageNumber,
      pageSize,
      condition
    }
  })
}

Api.prototype.dMusicGetInfo = function (MusicId) {
  return request({
    method: 'get',
    url: '/d-musics/website/open/getInfo',
    params: {
      MusicId
    }
  })
}

Api.prototype.dMusicGetWebsiteIndexMusics = function (needCover) {
  return request({
    method: 'get',
    url: '/d-musics/website/open/getWebsiteIndexMusics',
    params: {
      needCover
    }
  })
};

/** ========================DRecord============================ **/

Api.prototype.dRecordAdd = function (dRecord) {
  return request({
    method: 'put',
    url: '/d-record/add',
    data: {
      productId: dRecord.productId,
      productCoverUrl: dRecord.productCoverUrl,
    }
  })
};

Api.prototype.dRecordRemove = function (dRecordId) {
  return request({
    method: 'delete',
    url: '/d-record/delete',
    params: {
      dRecordId
    }
  })
};

Api.prototype.dRecordUpdate = function (dRecord) {
  return request({
    method: 'post',
    url: '/d-record/update',
    data: {
      id: dRecord.id,
      productId: dRecord.productId,
      productCoverUrl: dRecord.productCoverUrl,
    }
  })
};

Api.prototype.dRecordGetOff = function (dRecordId) {
  return request({
    method: 'delete',
    url: '/d-record/getOff',
    params: {
      dRecordId
    }
  })
};

Api.prototype.dRecordGetRecommendRecord = function (pageNumber, pageSize, condition) {
  return request({
    method: 'get',
    url: '/d-record/website/open/getRecommendRecord',
    params: {
      pageNumber, pageSize, condition
    }
  })
};

Api.prototype.dRecordGetIndexCubeRecommend = function () {
  return request({
    method: 'get',
    url: '/d-record/website/open/getIndexCubeRecommend',
  })
};


/** ========================UCollection============================ **/

Api.prototype.uCollectionAdd = function (uCollections) {
  return request({
    method: 'put',
    url: '/u-collections/add',
    data: {
      userId: uCollections.userId,
      productId: uCollections.productId,
      productName: uCollections.productName,
      productCoverUrl: uCollections.productCoverUrl
    }
  })
};

Api.prototype.uCollectionRemove = function (uCollections) {
  return request({
    method: 'delete',
    url: '/u-collections/delete',
    params: {
      userId: uCollections.userId,
      productId: uCollections.productId
    }
  })
};

Api.prototype.uCollectionGetAllCollections = function (pageNumber, pageSize, condition) {
  return request({
    method: 'get',
    url: '/u-collections/getAllCollections',
    params: {
      pageNumber, pageSize, condition
    }
  })
};

Api.prototype.uCollectionGetUserCollections = function (userId) {
  return request({
    method: 'get',
    url: '/u-collections/getUserCollections',
    params: {
      userId
    }
  })
};

/** ========================uComments============================ **/

Api.prototype.uCommentsAdd = function (uComments) {
  return request({
    method: 'put',
    url: '/u-comments/add',
    data: {
      userId: uComments.userId,
      productId: uComments.productId,
      userNickname: uComments.userNickname,
      avatarUrl: uComments.avatarUrl,
      score: uComments.score,
      comment: uComments.comment
    }
  })
};


Api.prototype.uCommentsRemove = function (commentId) {
  return request({
    method: 'delete',
    url: '/u-comments/delete',
    params: {
      commentId
    }
  })
};


Api.prototype.uCommentsGetAllComments = function (pageNumber, pageSize, condition) {
  return request({
    method: 'get',
    url: '/u-comments/getAllComments',
    params: {
      pageNumber, pageSize, condition
    }
  })
};

Api.prototype.uCommentsGetUserComments = function (userId) {
  return request({
    method: 'get',
    url: '/u-comments/getUserComments',
    params: {
      userId
    }
  })
};

Api.prototype.uCommentsGetOff = function (commentId) {
  return request({
    method: 'delete',
    url: '/u-comments/off',
    params: {
      commentId
    }
  })
};

Api.prototype.uCommentsGetProductionComments = function (productId, pageNumber, pageSize) {
  return request({
    method: 'get',
    url: '/u-comments/website/open/getProductionComments',
    params: {
      productId, pageNumber, pageSize
    }
  })
};

/** ========================uComplaints============================ **/

Api.prototype.uComplaintsAdd = function (uComplaints) {
  return request({
    method: 'put',
    url: '/u-complaints/add',
    data: {
      userId: uComplaints.userId,
      complaintContent: uComplaints.complaintContent,
      handler: '',
      handlerReply: '',
    }
  })
};


Api.prototype.uComplaintsRemove = function (uComplaintId) {
  return request({
    method: 'delete',
    url: '/u-complaints/delete',
    params: {
      uComplaintId
    }
  })
};


Api.prototype.uComplaintsGetAllComplaints = function (pageNumber, pageSize, condition) {
  return request({
    method: 'get',
    url: '/u-complaints/getAllComplaints',
    params: {
      pageNumber, pageSize, condition
    }
  })
};

Api.prototype.uComplaintsGetComplaint = function (uComplaintId) {
  return request({
    method: 'get',
    url: '/u-complaints/getComplaint',
    params: {
      uComplaintId
    }
  })
};

Api.prototype.uComplaintsGetHandlerComplaints = function (pageNumber, pageSize, handle) {
  return request({
    method: 'get',
    url: '/u-complaints/getHandlerComplaints',
    params: {
      pageNumber, pageSize, handle
    }
  })
};

Api.prototype.uComplaintsGetUserComplaints = function (userId) {
  return request({
    method: 'get',
    url: '/u-complaints/getUserComplaints',
    params: {
      userId
    }
  })
};

Api.prototype.uComplaintsHandle = function (uComplaints) {
  return request({
    method: 'post',
    url: '/u-complaints/handle',
    data: {
      id: uComplaints.id,
      userId: uComplaints.userId,
      complaintContent: uComplaints.complaintContent,
      handler: uComplaints.handler,
      handlerReply: uComplaints.handlerReply,
    }
  })
};

Api.prototype.uComplaintsUpdate = function (uComplaints) {
  return request({
    method: 'post',
    url: '/u-complaints/update',
    data: {
      id: uComplaints.id,
      userId: uComplaints.userId,
      complaintContent: uComplaints.complaintContent,
      handler: uComplaints.handler,
      handlerReply: uComplaints.handlerReply,
      state: uComplaints.state
    }
  })
};

/** ========================uReport============================ **/

Api.prototype.uReportAdd = function (uReport) {
  return request({
    method: 'put',
    url: '/u-report/add',
    data: {
      userId: uReport.userId,
      commentId: uReport.commentId,
      reportType: uReport.reportType,
      handler: '',
      handlerReply: '',
    }
  })
};


Api.prototype.uReportRemove = function (uReportId) {
  return request({
    method: 'delete',
    url: '/u-report/delete',
    params: {
      uReportId
    }
  })
};


Api.prototype.uReportGetAllReports = function (pageNumber, pageSize, condition) {
  return request({
    method: 'get',
    url: '/u-report/getAllReport',
    params: {
      pageNumber, pageSize, condition
    }
  })
};

Api.prototype.uReportGetReport = function (uReportId) {
  return request({
    method: 'get',
    url: '/u-report/getReport',
    params: {
      uReportId
    }
  })
};

Api.prototype.uReportGetHandlerReport = function (pageNumber, pageSize, handle) {
  return request({
    method: 'get',
    url: '/u-report/getHandlerReport',
    params: {
      pageNumber, pageSize, handle
    }
  })
};

Api.prototype.uReportGetUserReport = function (userId) {
  return request({
    method: 'get',
    url: '/u-report/getUserReport',
    params: {
      userId
    }
  })
};

Api.prototype.uReportHandle = function (uReport) {
  return request({
    method: 'post',
    url: '/u-report/handle',
    data: {
      id: uReport.id,
      userId: uReport.userId,
      commentId: uReport.commentId,
      reportType: uReport.reportType,
      handler: '',
      handlerReply: uReport.handlerReply,
    }
  })
};

Api.prototype.uReportUpdate = function (uReport) {
  return request({
    method: 'post',
    url: '/u-report/update',
    data: {
      id: uReport.id,
      userId: uReport.userId,
      commentId: uReport.commentId,
      reportType: uReport.reportType,
      handler: uReport.handler,
      handlerReply: uReport.handlerReply,
      state: uReport.state
    }
  })
};

/** ========================uScore============================ **/

Api.prototype.uScoreGetAllScoreData = function (pageNumber, pageSize, condition) {
  return request({
    method: 'get',
    url: '/u-score/getAllScoreData',
    params: {
      pageNumber, pageSize, condition
    }
  })
};

Api.prototype.uScoreUpdate = function (score) {
  return request({
    method: 'post',
    url: '/u-score/update',
    data: {
      id: score.id,
      productId: score.productId,
      productType: score.productType,
      oneStar: score.oneStar,
      twoStar: score.twoStar,
      threeStar: score.threeStar,
      fourStar: score.fourStar,
      fiveStar: score.fiveStar,
      total: score.total,
      score: score.score,
      sevenVisit: score.sevenVisit,
      recordDateVisit: score.recordDateVisit,
      todayVisit: score.todayVisit,
      yesterdayVisit: score.yesterdayVisit,
      postDate: score.postDate.format("yyyy-MM-dd"),
      statisticPoint: score.statisticPoint,
    }
  })
};

Api.prototype.uScoreGetNewProduction = function (pageNumber, pageSize, productType) {
  return request({
    method: 'get',
    url: '/u-score/website/open/getNewProduction',
    params: {
      pageNumber, pageSize, productType
    }
  })
};

Api.prototype.uScoreGetOtherStatistical = function (pageNumber, pageSize, productType) {
  return request({
    method: 'get',
    url: '/u-score/website/open/getOtherStatistical',
    params: {
      pageNumber, pageSize, productType
    }
  })
};

Api.prototype.uScoreGetScoreStatistical = function (pageNumber, pageSize, productType) {
  return request({
    method: 'get',
    url: '/u-score/website/open/getScoreStatistical',
    params: {
      pageNumber, pageSize, productType
    }
  })
};

Api.prototype.uScoreGetTotalStatistical = function (pageNumber, pageSize, productType) {
  return request({
    method: 'get',
    url: '/u-score/website/open/getTotalStatistical',
    params: {
      pageNumber, pageSize, productType
    }
  })
};

Api.prototype.uScoreVisitRecord = function (productId) {
  return request({
    method: 'get',
    url: '/u-score/website/open/visit',
    params: {
      productId
    }
  })
};

/** ========================uScore============================ **/

Api.prototype.userLogin = function (username, password) {
  return request({
    method: 'post',
    url: '/user/account/login',
    params: {
      username, password
    }
  }).then(rs => {
    let user = {
      username: username,
      password: password,
      accessToken: rs.data.token,
      tokenPassTime: new Date().getTime() + 28*60*1000
    }
    console.log(user.accessToken);
    sessionStorage.setItem("website-user", JSON.stringify(user));
    return rs;
  })
};

Api.prototype.userAddAdministration = function (user) {
  return request({
    method: 'put',
    url: '/user/account/addAdministration',
    data: {
      username: user.username,
      password: user.password,
      nickName: user.nickName,
      gender: user.gender,
      telephone: user.telephone,
      email: user.email,
      headPortrait: user.headPortrait,
      role: user.role
    }
  })
};

Api.prototype.userRegister = function (user) {
  return request({
    method: 'put',
    url: '/user/account/register',
    data: {
      username: user.username,
      password: user.password,
      nickName: user.nickName,
      gender: user.gender,
      telephone: user.telephone,
      email: user.email,
      headPortrait: user.headPortrait,
      verifyCode: user.verifyCode
    }
  })
};

Api.prototype.userAdminUserInfoUpdate = function (user) {
  return request({
    method: 'post',
    url: '/user/account/adminUserInfoUpdate',
    data: {
      username: user.username,
      password: user.password,
      nickName: user.nickName,
      gender: user.gender,
      telephone: user.telephone,
      email: user.email,
      headPortrait: user.headPortrait,
      role: user.role
    }
  })
};

Api.prototype.userClientUserInfoUpdate = function (user) {
  return request({
    method: 'post',
    url: '/user/account/clientUserInfoUpdate',
    data: {
      username: user.username,
      password: user.password,
      nickName: user.nickName,
      gender: user.gender,
      telephone: user.telephone,
      email: user.email,
      headPortrait: user.headPortrait,
      role: user.role
    }
  })
};

Api.prototype.userVerifyCodeOne = function (post, info) {
  return request({
    method: 'get',
    url: '/user/account/email/code',
    params: {
      post, info
    }
  })
};

Api.prototype.userRemove = function (userId) {
  return request({
    method: 'delete',
    url: '/user/account/remove',
    params: {
      userId
    }
  })
};

Api.prototype.userForbidden = function (userId) {
  return request({
    method: 'delete',
    url: '/user/account/forbidden',
    params: {
      userId
    }
  })
};

Api.prototype.userSearchAllAdminUser = function (pageNumber, pageSize, condition) {
  return request({
    method: 'get',
    url: '/user/account/searchAllAdminUser',
    params: {
      pageNumber, pageSize, condition
    }
  })
};

Api.prototype.userSearchAllClientUser = function (pageNumber, pageSize, condition) {
  return request({
    method: 'get',
    url: '/user/account/searchAllClientUser',
    params: {
      pageNumber, pageSize, condition
    }
  })
};

Api.prototype.userVerifyCodeTwo = function (user, info) {
  return request({
    method: 'get',
    url: '/user/account/user/VerifyCode',
    params: {
      user, info
    }
  })
};

Api.prototype.userPasswordAlter = function (user) {
  return request({
    method: 'post',
    url: '/user/account/userPasswordAlter',
    data: {
      email: user.email,
      password: user.password,
      verifyCode: user.verifyCode
    }
  })
};

Api.prototype.userInfo = function () {
  return request({
    method: 'get',
    url: '/user/info',
  })
};


export default api;
