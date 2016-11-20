package ar.edu.itba.pod.hz.model;

public class Pair<K, V> {
    public final K fst;
    public final V snd;

    public Pair(K fst, V snd) {
        this.fst = fst;
        this.snd = snd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair<?, ?> pair = (Pair<?, ?>) o;

        if (fst != null ? !fst.equals(pair.fst) : pair.fst != null) return false;
        return snd != null ? snd.equals(pair.snd) : pair.snd == null;

    }

    @Override
    public int hashCode() {
        int result = fst != null ? fst.hashCode() : 0;
        result = 31 * result + (snd != null ? snd.hashCode() : 0);
        return result;
    }

}
