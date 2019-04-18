import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import su.KosAVLTreeSet;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class TestsWithLong {

    private Set<Long> treeSet;
    private Set<Long> kosAVLTreeSet;
    private List<Long> list;

    private final long leftForTrees = -100000L;
    private final long rightForTrees = 100000L;

    private final long leftForList = -10000L;
    private final long rightForList = 10000L;

    @Before
    public void setup() {

        list = populate();
        treeSet = new TreeSet<>();
        kosAVLTreeSet = new KosAVLTreeSet<>();
        IntStream.range(0, 100000).forEach(i -> {
            long l = leftForTrees + (long) (Math.random() * (rightForTrees - leftForTrees));
            treeSet.add(l);
            kosAVLTreeSet.add(l);
        });
    }

    private List<Long> populate() {
        return LongStream.range(0, 10000L)
                .map(it -> leftForList + (long) (Math.random() * (rightForList - leftForList)))
                .boxed()
                .collect(Collectors.toList());
    }

    @Test
    public void check() {
        Assert.assertEquals(treeSet, kosAVLTreeSet);
    }

    @Test
    public void checkViceVersa() {
        Assert.assertEquals(kosAVLTreeSet, treeSet);
    }

    @Test
    public void checkToArray(){
        Object[] arrayFromTree = treeSet.toArray();
        Object[] arrayFromKos = kosAVLTreeSet.toArray();
        Assert.assertArrayEquals(arrayFromTree,arrayFromKos);
    }

    @Test
    public void checkToArrayLong(){
        Long[] array = new Long[treeSet.size()];
        Long[] arrayFromTree = treeSet.toArray(array);
        Long[] arrayFromKos = kosAVLTreeSet.toArray(array);
        Assert.assertArrayEquals(arrayFromTree,arrayFromKos);
    }

    @Test
    public void checkDesNavigableSet() {
        NavigableSet<Long> desTreeSet = ((NavigableSet<Long>) treeSet).descendingSet();

        NavigableSet<Long> desKosAVLTreeSet = ((NavigableSet<Long>) kosAVLTreeSet).descendingSet();

        Assert.assertEquals(desTreeSet, desKosAVLTreeSet);
    }

    @Test
    public void headNavSet() {
        NavigableSet<Long> headTreeSet = ((NavigableSet<Long>) treeSet).headSet(100L, true);

        NavigableSet<Long> headKosAVLTreeSet = ((NavigableSet<Long>) kosAVLTreeSet).headSet(100L, true);

        System.out.println("Размеры хэд сэтов");
        System.out.println(headTreeSet.size());
        System.out.println(headKosAVLTreeSet.size());

        Assert.assertEquals(headKosAVLTreeSet, headTreeSet);
    }

    @Test
    public void tailNavSet() {
        NavigableSet<Long> tailTreeSet = ((NavigableSet<Long>) treeSet).tailSet(100L, true);

        NavigableSet<Long> tailKosAVLTreeSet = ((NavigableSet<Long>) kosAVLTreeSet).tailSet(100L, true);

        System.out.println("Размеры тэил сэтов");
        System.out.println(tailTreeSet.size());
        System.out.println(tailKosAVLTreeSet.size());

        Assert.assertEquals(tailTreeSet, tailKosAVLTreeSet);
    }

    @Test
    public void subNavSet() {

        NavigableSet<Long> subTreeSet = ((NavigableSet<Long>) treeSet).subSet(0L, true, 10000L, true);

        NavigableSet<Long> subKosAVLTreeSet = ((NavigableSet<Long>) kosAVLTreeSet).subSet(0L, true, 10000L, true);

        System.out.println("Размеры саб сэтов");
        System.out.println(subTreeSet.size());
        System.out.println(subKosAVLTreeSet.size());

        Assert.assertEquals(subTreeSet, subKosAVLTreeSet);
    }

    @Test
    public void headSet() {
        SortedSet<Long> headTreeSet = ((NavigableSet<Long>) treeSet).headSet(100L);

        SortedSet<Long> headKosAVLTreeSet = ((NavigableSet<Long>) kosAVLTreeSet).headSet(100L);

        System.out.println("Размеры хэд сэтов");
        System.out.println(headTreeSet.size());
        System.out.println(headKosAVLTreeSet.size());

        Assert.assertEquals(headTreeSet, headKosAVLTreeSet);
    }

    @Test
    public void tailSet() {
        SortedSet<Long> tailTreeSet = ((NavigableSet<Long>) treeSet).tailSet(100L);

        SortedSet<Long> tailKosAVLTreeSet = ((NavigableSet<Long>) kosAVLTreeSet).tailSet(100L);

        System.out.println("Размеры тэил сэтов");
        System.out.println(tailTreeSet.size());
        System.out.println(tailKosAVLTreeSet.size());

        Assert.assertEquals(tailTreeSet, tailKosAVLTreeSet);
    }

    @Test
    public void subSet() {

        SortedSet<Long> subTreeSet = ((NavigableSet<Long>) treeSet).subSet(0L, 10000L);

        SortedSet<Long> subKosAVLTreeSet = ((NavigableSet<Long>) kosAVLTreeSet).subSet(0L, 10000L);

        System.out.println("Размеры саб сэтов");
        System.out.println(subTreeSet.size());
        System.out.println(subKosAVLTreeSet.size());

        Assert.assertEquals(subTreeSet, subKosAVLTreeSet);
    }

    @Test
    public void ceiling() {

        Long ceilingTreeSet = ((NavigableSet<Long>) treeSet).ceiling(0L);

        Long ceilingKosAVLTreeSet = ((NavigableSet<Long>) kosAVLTreeSet).ceiling(0L);

        System.out.println("ceiling 0L");
        System.out.println(ceilingTreeSet);
        System.out.println(ceilingKosAVLTreeSet);

        Assert.assertEquals(ceilingTreeSet, ceilingKosAVLTreeSet);
    }

    @Test
    public void floor() {

        Long floorTreeSet = ((NavigableSet<Long>) treeSet).floor(0L);

        Long floorKosAVLTreeSet = ((NavigableSet<Long>) kosAVLTreeSet).floor(0L);

        System.out.println("floor 0L");
        System.out.println(floorTreeSet);
        System.out.println(floorKosAVLTreeSet);

        Assert.assertEquals(floorTreeSet, floorKosAVLTreeSet);
    }

    @Test
    public void higher() {

        Long higherTreeSet = ((NavigableSet<Long>) treeSet).higher(0L);

        Long higherKosAVLTreeSet = ((NavigableSet<Long>) kosAVLTreeSet).higher(0L);

        System.out.println("higher 0L");
        System.out.println(higherTreeSet);
        System.out.println(higherKosAVLTreeSet);

        Assert.assertEquals(higherTreeSet, higherKosAVLTreeSet);
    }

    @Test
    public void lower() {

        Long lowerTreeSet = ((NavigableSet<Long>) treeSet).lower(0L);

        Long lowerKosAVLTreeSet = ((NavigableSet<Long>) kosAVLTreeSet).lower(0L);

        System.out.println("lower 0L");
        System.out.println(lowerTreeSet);
        System.out.println(lowerKosAVLTreeSet);

        Assert.assertEquals(lowerTreeSet, lowerKosAVLTreeSet);
    }

    @Test
    public void retainAll(){
        System.out.println("Размеры начальных сэтов");
        System.out.println(treeSet.size());
        System.out.println(kosAVLTreeSet.size());
        kosAVLTreeSet.retainAll(list);
        treeSet.retainAll(list);
        System.out.println("Размеры сэтов после retain");
        System.out.println(treeSet.size());
        System.out.println(kosAVLTreeSet.size());
        Assert.assertEquals(treeSet, kosAVLTreeSet);
    }

    @Test
    public void addAll() {
        System.out.println("Размеры начальных сэтов");
        System.out.println(treeSet.size());
        System.out.println(kosAVLTreeSet.size());
        kosAVLTreeSet.addAll(list);
        treeSet.addAll(list);
        System.out.println("Размеры сэтов после добавления");
        System.out.println(treeSet.size());
        System.out.println(kosAVLTreeSet.size());
        Assert.assertEquals(treeSet, kosAVLTreeSet);
    }

    @Test
    public void deleteAll() {
        System.out.println("Размеры начальных сэтов");
        System.out.println(treeSet.size());
        System.out.println(kosAVLTreeSet.size());
        kosAVLTreeSet.removeAll(list);
        treeSet.removeAll(list);
        System.out.println("Размеры сэтов после удаления");
        System.out.println(treeSet.size());
        System.out.println(kosAVLTreeSet.size());
        Assert.assertEquals(kosAVLTreeSet, treeSet);
    }

}
