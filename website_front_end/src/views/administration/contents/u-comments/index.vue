<template>
  <div id="data_wrap">
    <header id="wrap_header">
      <div id="search_wrap">
        <Input v-model="keyword" size="large" search enter-button placeholder="请输入要查询的条件信息" @keyup.enter.native="searchForm" @click.native="searchForm"/>
      </div>
    </header>
    <div id="add_table">
      <Table no-data-text="暂无数据" height="535" max-height="540" border :columns="columns" :data="data">
        <template slot-scope="{ row }" slot="name">
          <strong>{{ row.name }}</strong>
        </template>
        <template slot-scope="{ row }" slot="productType">
          {{ row.productType === 1 ? '书籍' : row.productType === 2 ? '电影' : '音乐' }}
        </template>
        <template slot-scope="{ row }" slot="productCoverUrl">
          {{ row.productCoverUrl ? row.productCoverUrl : '暂无数据' }}
        </template>
        <template slot-scope="{ row }" slot="comment">
          {{ row.comment.length >= 46 ? row.comment.substr(0, 46) + '……' : row.comment }}
        </template>
        <template slot-scope="{ row,index }" slot="state">
          <Button :type="row.state === 1?'warning':'success'" size="small" style="margin-right: 5px"
                  @click="getOff(index)">{{ row.state === 1 ? '下架' : '上架' }}
          </Button>
        </template>
        <template slot-scope="{ row, index }" slot="action">
          <Button type="primary" size="small" style="margin-right: 5px" @click="show(index)">查看
            <Icon type="md-clipboard"/>
          </Button>
          <Button type="error" size="small" @click="remove(index)">删除
            <Icon type="md-trash"/>
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
          width: 120,
          sortable: true
        },
        {
          title: '用户编号',
          key: 'userId',
          width: 120,
          sortable: true
        },
        {
          title: '产品编号',
          key: 'productId',
          width: 130,
          sortable: true
        },
        {
          title: '类型',
          slot: 'productType',
          width: 75
        },
        {
          title: '用户昵称',
          key: 'userNickname',
          width: 95
        },
        {
          title: '评分',
          key: 'score',
          width: 85,
          sortable: true
        },
        {
          title: '评论内容',
          slot: 'comment',
          width: 380,
        },
        {
          title: '评论时间',
          key: 'commentTime',
          width: 170,
          sortable: true
        }, {
          title: '状态',
          slot: 'state',
          width: 90,
          filters: [
            {
              label: '上架中',
              value: 1
            },
            {
              label: '已下架',
              value: 2
            }
          ],
          filterMultiple: false,
          filterMethod(value, row) {
            if (value === 1) {
              return row.state === 1;
            } else if (value === 2) {
              return row.state === 0;
            }
          }
        },
        {
          title: '操作',
          slot: 'action',
          width: 175,
          align: 'center'
        }
      ],
      data: [],
      total: null,
      page: 1
    }
  },
  watch: {
    //watch可以监听data中指定数据的变化，然后触发对应的处理函数
    keyword: function (newVal) {
      if (newVal === '') {
        this.api.uCommentsGetAllComments(1, 10, 0).then(rs => {
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
      this.api.uCommentsGetAllComments(1, 10, this.keyword === '' ? 0 : this.keyword).then(rs => {
        this.data = rs.data.data.records
        this.total = rs.data.data.total + 1
      }).catch(err => {
        console.log(err);
      })
    },
    show(index) {
      this.$Modal.info({
        title: '用户评论信息',
        content: `<b>ID：</b>${this.data[index].id}<br>
          <b>文化产品编号：</b>${this.data[index].productId}<br>
          <b>类型：</b>${this.data[index].productType === 1 ? '书籍' : this.data[index].productType === 2 ? '电影' : '音乐'}<br>
          <b>用户编号：</b>${this.data[index].userId}<br>
          <b>用户昵称：</b>${this.data[index].userNickname}<br>
          <b>用户头像：</b>${this.data[index].avatarUrl}<br>
          <b>评分：</b>${this.data[index].score}<br>
          <b>评论：</b>${this.data[index].comment}<br>
          <b>评论时间：</b>${this.data[index].commentTime}<br>
          <b>状态：</b>${this.state === 1 ? '下架' : '上架'}<br>`
      })
    },
    remove(index) {
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.api.uCommentsRemove(this.data[index].id).then(rs => {
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
      this.$confirm(this.data[index].state === 1 ? '要将此列下架?' : '要将此列重新上架?', '提示', {
        confirmButtonText: this.data[index].state === 1 ? '下架?' : '上架',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.api.uCommentsGetOff(this.data[index].id).then(rs => {
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
      sessionStorage.setItem('uCommentsPage', page);
      this.api.uCommentsGetAllComments(page, 10, this.keyword === '' ? 0 : this.keyword).then(rs => {
        this.data = rs.data.data.records
        this.total = rs.data.data.total + 1
      }).catch(err => {
        console.log(err);
      })
    }
  },
  created() {
    if (sessionStorage.getItem('uCommentsPage')) {
      this.page = parseInt(sessionStorage.getItem('uCommentsPage'))
    }
    this.api.uCommentsGetAllComments(this.page, 10, 0).then(rs => {
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
