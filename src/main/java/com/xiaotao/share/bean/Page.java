package com.xiaotao.share.bean;

public class Page<T> extends com.baomidou.mybatisplus.extension.plugins.pagination.Page<T> {
    private static final long serialVersionUID = 4194745242035500084L;

    private long pages;

    @Override
    public long getPages() {
        return super.getPages();
    }

}
