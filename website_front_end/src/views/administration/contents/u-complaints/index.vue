<template>
  <div id="data_wrap">
    <header id="wrap_header">
      <div id="search_wrap">
        <Input v-model="keyword" size="large" search enter-button placeholder="请输入要查询的条件信息" @keyup.enter.native="searchForm" @click.native="searchForm"/>
      </div>
      <div id="add_drawer">
        <Radio-group v-model="handle">
          <Radio label="2">
            <span>全部</span>
          </Radio>
          <Radio label="0">
            <span>未处理</span>
          </Radio>
          <Radio label="1">
            <span>已处理</span>
          </Radio>
        </Radio-group>
      </div>
    </header>
    <div id="add_table">
      <Table no-data-text="暂无数据" height="535" max-height="540" border :columns="columns" :data="data">
        <template slot-scope="{ row }" slot="name">
          <strong>{{ row.name }}</strong>
        </template>
        <template slot-scope="{ row }" slot="complaintContent">
          {{
            row.complaintContent === null || '' ? '无内容' : row.complaintContent.length > 38 ? row.complaintContent.substr(0, 38) + "……" : row.complaintContent
          }}
        </template>
        <template slot-scope="{ row }" slot="handler">
          {{ row.handler === null || '' ? '暂无' : row.handler }}
        </template>
        <template slot-scope="{ row }" slot="handlerReply">
          {{
            row.handlerReply === null || '' ? '暂无内容' : row.handlerReply.length > 38 ? row.handlerReply.substr(0, 38) + "……" : row.handlerReply
          }}
        </template>
        <template slot-scope="{ row }" slot="state">
          <span>
            <handle :operatorUserRole="operatorUserRole" :optionData="row"/>
          </span>
        </template>
        <template slot-scope="{ row, index }" slot="action">
          <Button type="primary" size="small" style="margin-right: 5px" @click="show(index)">查看
            <Icon type="md-clipboard"/>
          </Button>
          <span>
            <update :operatorUserRole="operatorUserRole" :optionData="row"/>
          </span>
          <Button :disabled="operatorUserRole !== 'root'" type="error" size="small" @click="remove(index)">删除
            <Icon type="md-trash"/>
          </Button>
        </template>
      </Table>
    </div>
    <Page style="margin-top: 1rem" :current="page" :total="total" @on-change="onPage"/>
  </div>
</template>

<script>
import Handle from './handle'
import Update from "./update";

export default {
  name: "index",
  components: {Handle,Update},
  data() {
    return {
      keyword: '',
      handle: '',
      operatorUserRole: '',
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
          width: 120
        },
        {
          title: '投诉内容',
          slot: 'complaintContent',
          width: 250
        },
        {
          title: '处理人',
          slot: 'handler',
          width: 115
        },
        {
          title: '处理回复',
          slot: 'handlerReply',
          width: 210
        },
        {
          title: '操作时间',
          key: 'operationTime',
          width: 170,
          sortable: true
        },
        {
          title: '操作人',
          key: 'operationUser',
          width: 125
        }, {
          title: '状态',
          slot: 'state',
          width: 90,
          filters: [
            {
              label: '未处理',
              value: 1
            },
            {
              label: '已处理',
              value: 2
            }
          ],
          filterMultiple: false,
          filterMethod(value, row) {
            if (value === 1) {
              return row.state === 0;
            } else if (value === 2) {
              return row.state === 1;
            }
          }
        },
        {
          title: '操作',
          slot: 'action',
          width: 235,
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
        this.api.uComplaintsGetAllComplaints(1, 10, newVal).then(rs => {
          this.data = rs.data.data.records
          this.total = rs.data.data.total + 1
        }).catch(err => {
          console.log(err);
        })
      }
    },
    handle: function (newVal) {
      if (newVal === '2') {
        this.api.uComplaintsGetAllComplaints(1, 10, '').then(rs => {
          this.data = rs.data.data.records
          this.total = rs.data.data.total + 1
        }).catch(err => {
          console.log(err);
        })
      } else {
        this.api.uComplaintsGetHandlerComplaints(1, 10, parseInt(newVal)).then(rs => {
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
      this.api.uComplaintsGetAllComplaints(1, 10, this.keyword).then(rs => {
        this.data = rs.data.data.records
        this.total = rs.data.data.total + 1
      }).catch(err => {
        console.log(err);
      })
    },
    show(index) {
      this.$Modal.info({
        title: '投诉信息',
        content: `<b>ID：</b>${this.data[index].id}<br>
          <b>用户编号：</b>${this.data[index].userId}<br>
          <b>投诉内容：</b>${this.data[index].complaintContent === null || '' ? '无' : this.data[index].complaintContent}<br>
          <b>处理人：</b>${this.data[index].handler === null || '' ? '暂无' : this.data[index].handler}<br>
          <b>处理回复：</b>${this.data[index].handlerReply === null || '' ? '暂无内容' : this.data[index].handlerReply}<br>
          <b>操作时间：</b>${this.data[index].operationTime}<br>
          <b>操作人：</b>${this.data[index].operationUser}<br>
          <b>状态：</b>${this.data[index].state === 0 ? '未处理' : '已处理'}<br>`
      })
    },
    remove(index) {
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.api.uComplaintsRemove(this.data[index].id).then(rs => {
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
    onPage(page) {
      sessionStorage.setItem('uComplaintsPage', page);
      location.reload();
    }
  },
  created() {
    this.operatorUserRole = JSON.parse(sessionStorage.getItem('website-user-info')).role
    if (sessionStorage.getItem('uComplaintsPage')) {
      this.page = parseInt(sessionStorage.getItem('uComplaintsPage'))
    }
    this.api.uComplaintsGetAllComplaints(this.page, 10, '').then(rs => {
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
