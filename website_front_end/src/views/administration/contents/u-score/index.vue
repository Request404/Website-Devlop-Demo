<template>
  <div id="data_wrap">
    <header id="wrap_header">
      <div id="search_wrap">
        <Input v-model="keyword" type="number" size="large" search enter-button placeholder="请输入要查询的条件信息" @keyup.enter.native="searchForm" @click.native="searchForm"/>
      </div>
    </header>
    <div id="add_table">
      <Table no-data-text="暂无数据" height="535" max-height="540" border :columns="columns" :data="data">
        <template slot-scope="{ row }" slot="name">
          <strong>{{ row.name }}</strong>
        </template>
        <template slot-scope="{ row }" slot="productCoverUrl">
          {{ row.productCoverUrl ? row.productCoverUrl : '暂无数据' }}
        </template>
        <template slot-scope="{ row }" slot="productType">
          {{ row.productType === 1 ? '书籍' : row.productType === 2 ? '电影' : '音乐' }}
        </template>
        <template slot-scope="{ row, index }" slot="action">
          <Button type="primary" size="small" style="margin-right: 5px" @click="show(index)">查看
            <Icon type="md-clipboard"/>
          </Button>
        </template>
      </Table>
    </div>
    <Page style="margin-top: 1rem" :current="page" :total="total" @on-change="onPage"/>
  </div>
</template>

<script>

export default {
  name: "index",
  data() {
    return {
      keyword: '',
      columns: [
        {
          title: 'ID',
          key: 'id',
          width: 117,
          sortable: true
        },
        {
          title: '产品编号',
          key: 'productId',
          width: 117
        },
        {
          title: '产品类型',
          slot: 'productType',
          width: 100
        },
        {
          title: '评分总人数',
          key: 'total',
          width: 115
        },
        {
          title: '总得分',
          key: 'score',
          width: 100
        },
        {
          title: '七天总访问数',
          key: 'sevenVisit',
          width: 125
        },
        {
          title: '七天访问人数',
          key: 'recordDateVisit',
          width: 145
        },
        {
          title: '昨日访问数',
          key: 'yesterdayVisit',
          width: 110
        },
        {
          title: '今日访问数',
          key: 'todayVisit',
          width: 110
        },
        {
          title: '上架日期',
          key: 'postDate',
          width: 180
        },
        {
          title: '产品统计得分',
          key: 'statisticPoint',
          width: 125
        },
        {
          title: '操作',
          slot: 'action',
          width: 90,
          align: 'center'
        }
      ],
      data: [],
      total: null,
      page: 1
    }
  },
  watch: {
    keyword: function (newVal) {
      if (newVal === ''){
        this.api.uScoreGetAllScoreData(0, 10, 0).then(rs => {
          this.data = rs.data.data.records
          this.total = rs.data.data.total + 1
        }).catch(err => {
          console.log(err);
        })
      }
    }
  },
  methods: {
    searchForm() {
      this.api.uScoreGetAllScoreData(0, 10, this.keyword === '' ? 0 : this.keyword).then(rs => {
        this.data = rs.data.data.records
        this.total = rs.data.data.total + 1
      }).catch(err => {
        console.log(err);
      })
    },
    show(index) {
      this.$Modal.info({
        title: '分数数据信息',
        content: `<b>ID：</b>${this.data[index].id}<br>
          <b>文化产品编号：</b>${this.data[index].productId}<br>
          <b>类型：</b>${this.data[index].productType === 1 ? '书籍' : this.data[index].productType === 2 ? '电影' : '音乐'}<br>
          <b>一星评分人数：</b>${this.data[index].oneStar}<br>
          <b>二星评分人数：</b>${this.data[index].twoStar}<br>
          <b>三星评分人数：</b>${this.data[index].threeStar}<br>
          <b>四星评分人数：</b>${this.data[index].fourStar}<br>
          <b>五星评分人数：</b>${this.data[index].fiveStar}<br>
          <b>评分总人数：</b>${this.data[index].total}<br>
          <b>产品统计得分：</b>${this.data[index].score}<br>
          <b>七天总访问人数：</b>${this.data[index].sevenVisit}<br>
          <b>七天访问人数：</b>${this.data[index].recordDateVisit}<br>
          <b>昨日访问人数：</b>${this.data[index].todayVisit}<br>
          <b>今日访问人数：</b>${this.data[index].yesterdayVisit}<br>
          <b>上架日期：</b>${this.data[index].postDate}<br>
          <b>产品得分：</b>${this.data[index].statisticPoint}<br>`
      })
    },
    remove(index) {
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.api.dRecordRemove(this.data[index].id).then(rs => {
          if (rs.data.data === 1) {
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
            location.reload();
          } else {
            this.$message.error('删除失败');
          }
        }).catch(err => {
          console.log(err);
          this.$message.error('删除失败');
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    getOff(index) {
      this.$confirm(this.data[index].state === 1 ? '要将次列下架?' : '要将此列重新上架?', '提示', {
        confirmButtonText: this.data[index].state === 1 ? '下架?' : '上架',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.api.dRecordGetOff(this.data[index].id).then(rs => {
          if (rs.data.data === 1) {
            this.$message({
              type: 'success',
              message: '操作成功!'
            });
            location.reload();
          } else {
            this.$message.error('操作失败');
          }
        }).catch(err => {
          console.log(err);
          this.$message.error('操作失败');
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: this.data[index].state === 1 ? '取消下架?' : '取消上架'
        });
      });
    },
    onPage(page) {
      sessionStorage.setItem('uScorePage', page);
      location.reload();
    }
  },
  created() {
    if (sessionStorage.getItem('uScorePage')) {
      this.page = parseInt(sessionStorage.getItem('uScorePage'))
    }
    this.api.uScoreGetAllScoreData(this.page, 10, 0).then(rs => {
      this.data = rs.data.data.records
      this.total = rs.data.data.total + 1
    }).catch(err => {
      console.log(err);
    })
  }
}
</script>

<style scoped lang="scss">
#data_wrap {
  width: 100%;
  height: 90%;
  display: flex;
  flex-direction: column;
  align-items: center;

  #wrap_header {
    width: 98%;
    height: 15%;
    display: flex;
    justify-content: space-between;
    align-items: center;

    #search_wrap {
      width: 45%;
      display: flex;
      align-items: center;

    }

    #add_drawer {
      width: auto;
      height: auto;
    }
  }

  #add_table {
    width: 98%;
  }
}

.demo-drawer-footer {
  width: 100%;
  position: absolute;
  bottom: 0;
  left: 0;
  border-top: 1px solid #e8e8e8;
  padding: 10px 16px;
  text-align: right;
  background: #fff;
}
</style>
