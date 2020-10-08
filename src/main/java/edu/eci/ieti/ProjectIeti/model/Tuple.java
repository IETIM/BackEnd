package edu.eci.ieti.ProjectIeti.model;

import org.springframework.data.annotation.Id;

import java.util.Objects;

public class Tuple<T1, T2> {
    @Id
    private String id;
    T1 o1;
    T2 o2;

    public Tuple(T1 o1, T2 o2) {
        super();
        this.o1 = o1;
        this.o2 = o2;
    }

    public T1 getElem1() {
        return o1;
    }

    public T2 getElem2() {
        return o2;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.o1);
        hash = 17 * hash + Objects.hashCode(this.o2);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tuple<?, ?> other = (Tuple<?, ?>) obj;
        if (!Objects.equals(this.o1, other.o1)) {
            return false;
        }
        if (!Objects.equals(this.o2, other.o2)) {
            return false;
        }
        return true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public T1 getO1() {
        return o1;
    }

    public void setO1(T1 o1) {
        this.o1 = o1;
    }

    public T2 getO2() {
        return o2;
    }

    public void setO2(T2 o2) {
        this.o2 = o2;
    }
}
