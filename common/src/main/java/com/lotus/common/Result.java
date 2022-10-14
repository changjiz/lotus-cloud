package com.lotus.common;

/**
 * @program: common
 * @description:
 * @author: Mark changji_z@163.com
 * @create: 2019-09-23 15:02
 **/
public class Result<L, R> {

    private L left;
    private R right;

    public Result(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public L getLeft() {
        return left;
    }

    public void setLeft(L left) {
        this.left = left;
    }

    public R getRight() {
        return right;
    }

    public void setRight(R right) {
        this.right = right;
    }
}
