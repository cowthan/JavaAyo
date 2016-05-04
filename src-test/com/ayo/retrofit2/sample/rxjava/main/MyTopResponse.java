package com.ayo.retrofit2.sample.rxjava.main;

import javax.security.auth.Subject;

public class MyTopResponse<T> implements TopResponse<T> {
	
	private int count;
    private int start;
    private int total;
    private String title;

    //用来模仿Data
    private T subjects;


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public T getSubjects() {
        return subjects;
    }

    public void setSubjects(T subjects) {
        this.subjects = subjects;
    }


    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("title=" + title + " count=" + count + " start=" + start);
        if (null != subjects) {
            sb.append(" subjects:" + subjects.toString());
        }
        return sb.toString();
    }

	@Override
	public boolean ok() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean msg() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public T body() {
		return subjects;
	}

}
