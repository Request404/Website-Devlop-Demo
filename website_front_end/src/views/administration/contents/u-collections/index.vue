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
        <template slot-scope="{ row }" slot="state">
          {{ row.state === 1 ? '上架中' : '已下架' }}
        </template>
        <template slot-scope="{ row, index }" slot="action">
          <Button type="primary" size="small" style="margin-right: 5px" @click="show(index)">查看封面
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
          title: '用户编号',
          key: 'userId',
          width: 140,
          sortable: true
        },
        {
          title: '文化产品编号',
          key: 'productId',
          width: 140
        },
        {
          title: '类型',
          slot: 'productType',
          width: 95
        },
        {
          title: '产品名',
          key: 'productName',
          width: 200
        },
        {
          title: '封面地址',
          slot: 'productCoverUrl',
          width: 400,
        },
        {
          title: '收藏时间',
          key: 'collectTime',
          width: 180,
          sortable: true
        }, {
          title: '状态',
          slot: 'state',
          width: 100,
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
          width: 180,
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
        this.api.uCollectionGetAllCollections(1, 10, 0).then(rs => {
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
      this.api.uCollectionGetAllCollections(1, 10, this.keyword === '' ? 0 : this.keyword).then(rs => {
        this.data = rs.data.data.records
        this.total = rs.data.data.total + 1
      }).catch(err => {
        console.log(err);
      })
    },
    show(index) {
      this.$Modal.info({
        title: '封面信息',
        content: `<img src="${this.data[index].productCoverUrl}" alt="">`
      })
    },
    onPage(page) {
      sessionStorage.setItem('uCommentsPage', page);
      this.api.uCollectionGetAllCollections(page, 10, this.keyword === '' ? 0 : this.keyword).then(rs => {
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
    this.api.uCollectionGetAllCollections(this.page, 10, 0).then(rs => {
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
