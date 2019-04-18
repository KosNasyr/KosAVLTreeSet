import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import su.KosAVLTreeSet;
import su.TestObj;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestWithTestObj {

    private Comparator<TestObj> cmr = Comparator.comparingInt(TestObj::hashCode);
    private Set<TestObj> treeSet;
    private Set<TestObj> kosAVLTreeSet;
    private List<TestObj> list;

    private final long leftForTrees = -100000L;
    private final long rightForTrees = 100000L;

    private final long leftForList = -10000L;
    private final long rightForList = 10000L;

    private final TestObj zero = TestObj.builder().id(0L).name("Name" + 0L).build();
    private final TestObj hndr = TestObj.builder().id(100L).name("Name" + 100L).build();
    private final TestObj tenth = TestObj.builder().id(10000L).name("Name" + 10000L).build();

    @Before
    public void setup() {

        list = populate();
        treeSet = new TreeSet<>(cmr);
        kosAVLTreeSet = new KosAVLTreeSet<>(cmr);

        IntStream.range(0, 100000).forEach(i -> {
            long l = leftForTrees + (long) (Math.random() * (rightForTrees - leftForTrees));
            TestObj to = TestObj.builder().id(l).name("Name" + l).build();
            treeSet.add(to);
            kosAVLTreeSet.add(to);
        });
    }

    private List<TestObj> populate() {
        return IntStream.range(0, 10000).mapToObj(i -> {
            long l = leftForList + (long) (Math.random() * (rightForList - leftForList));
            return TestObj.builder().id(l).name("Name" + l).build();
        }).collect(Collectors.toList());
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
    public void checkToArray() {
        Object[] arrayFromTree = treeSet.toArray();
        Object[] arrayFromKos = kosAVLTreeSet.toArray();
        Assert.assertArrayEquals(arrayFromTree, arrayFromKos);
    }

    @Test
    public void checkToArrayTestObj() {
        TestObj[] l = new TestObj[treeSet.size()];
        TestObj[] arrayFromTree = treeSet.toArray(l);
        TestObj[] arrayFromKos = kosAVLTreeSet.toArray(l);
        Assert.assertArrayEquals(arrayFromTree, arrayFromKos);
    }

    @Test
    public void checkToArrayTestObjVV() {
        TestObj[] l = new TestObj[treeSet.size()];
        TestObj[] arrayFromTree = treeSet.toArray(l);
        TestObj[] arrayFromKos = kosAVLTreeSet.toArray(l);
        Assert.assertArrayEquals(arrayFromKos, arrayFromTree);
    }

    @Test
    public void checkDesNavigableSet() {
        NavigableSet<TestObj> desTreeSet = ((NavigableSet<TestObj>) treeSet).descendingSet();

        NavigableSet<TestObj> desKosAVLTreeSet = ((NavigableSet<TestObj>) kosAVLTreeSet).descendingSet();

        Assert.assertEquals(desTreeSet, desKosAVLTreeSet);
    }

    @Test
    public void headNavSet() {
        NavigableSet<TestObj> headTreeSet = ((NavigableSet<TestObj>) treeSet).headSet(hndr, true);

        NavigableSet<TestObj> headKosAVLTreeSet = ((NavigableSet<TestObj>) kosAVLTreeSet).headSet(hndr, true);

        System.out.println("Размеры хэд сэтов");
        System.out.println(headTreeSet.size());
        System.out.println(headKosAVLTreeSet.size());

        Assert.assertEquals(headTreeSet, headKosAVLTreeSet);
    }

    @Test
    public void tailNavSet() {
        NavigableSet<TestObj> tailTreeSet = ((NavigableSet<TestObj>) treeSet).tailSet(hndr, true);

        NavigableSet<TestObj> tailKosAVLTreeSet = ((NavigableSet<TestObj>) kosAVLTreeSet).tailSet(hndr, true);

        System.out.println("Размеры тэил сэтов");
        System.out.println(tailTreeSet.size());
        System.out.println(tailKosAVLTreeSet.size());

        Assert.assertEquals(tailKosAVLTreeSet, tailTreeSet);
    }

    @Test
    public void subNavSet() {

        NavigableSet<TestObj> subTreeSet = ((NavigableSet<TestObj>) treeSet)
                .subSet(tenth, true, zero, true);

        NavigableSet<TestObj> subKosAVLTreeSet = ((NavigableSet<TestObj>) kosAVLTreeSet)
                .subSet(tenth, true, zero, true);

        System.out.println("Размеры саб сэтов");
        System.out.println(subTreeSet.size());
        System.out.println(subKosAVLTreeSet.size());

        Assert.assertEquals(subTreeSet, subKosAVLTreeSet);
    }

    @Test
    public void headSet() {
        SortedSet<TestObj> headTreeSet = ((NavigableSet<TestObj>) treeSet).headSet(hndr);

        SortedSet<TestObj> headKosAVLTreeSet = ((NavigableSet<TestObj>) kosAVLTreeSet).headSet(hndr);

        System.out.println("Размеры хэд сэтов");
        System.out.println(headTreeSet.size());
        System.out.println(headKosAVLTreeSet.size());

        Assert.assertEquals(headTreeSet, headKosAVLTreeSet);
    }

    @Test
    public void tailSet() {
        SortedSet<TestObj> tailTreeSet = ((NavigableSet<TestObj>) treeSet).tailSet(hndr);

        SortedSet<TestObj> tailKosAVLTreeSet = ((NavigableSet<TestObj>) kosAVLTreeSet).tailSet(hndr);

        System.out.println("Размеры тэил сэтов");
        System.out.println(tailTreeSet.size());
        System.out.println(tailKosAVLTreeSet.size());

        Assert.assertEquals(tailTreeSet, tailKosAVLTreeSet);
    }

    @Test
    public void subSet() {

        SortedSet<TestObj> subTreeSet = ((NavigableSet<TestObj>) treeSet).subSet(tenth, zero);

        SortedSet<TestObj> subKosAVLTreeSet = ((NavigableSet<TestObj>) kosAVLTreeSet).subSet(tenth, zero);

        System.out.println("Размеры саб сэтов");
        System.out.println(subTreeSet.size());
        System.out.println(subKosAVLTreeSet.size());

        Assert.assertEquals(subTreeSet, subKosAVLTreeSet);
    }

    @Test
    public void ceiling() {

        TestObj ceilingTreeSet = ((NavigableSet<TestObj>) treeSet).ceiling(zero);

        TestObj ceilingKosAVLTreeSet = ((NavigableSet<TestObj>) kosAVLTreeSet).ceiling(zero);

        System.out.println("ceiling 0L");
        System.out.println(ceilingTreeSet);
        System.out.println(ceilingKosAVLTreeSet);

        Assert.assertEquals(ceilingTreeSet, ceilingKosAVLTreeSet);
    }

    @Test
    public void floor() {

        TestObj floorTreeSet = ((NavigableSet<TestObj>) treeSet).floor(zero);

        TestObj floorKosAVLTreeSet = ((NavigableSet<TestObj>) kosAVLTreeSet).floor(zero);

        System.out.println("floor 0L");
        System.out.println(floorTreeSet);
        System.out.println(floorKosAVLTreeSet);

        Assert.assertEquals(floorTreeSet, floorKosAVLTreeSet);
    }

    @Test
    public void higher() {

        TestObj higherTreeSet = ((NavigableSet<TestObj>) treeSet).higher(zero);

        TestObj higherKosAVLTreeSet = ((NavigableSet<TestObj>) kosAVLTreeSet).higher(zero);

        System.out.println("higher 0L");
        System.out.println(higherTreeSet);
        System.out.println(higherKosAVLTreeSet);

        Assert.assertEquals(higherTreeSet, higherKosAVLTreeSet);
    }

    @Test
    public void lower() {

        TestObj lowerTreeSet = ((NavigableSet<TestObj>) treeSet).lower(zero);

        TestObj lowerKosAVLTreeSet = ((NavigableSet<TestObj>) kosAVLTreeSet).lower(zero);

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
        Assert.assertEquals(treeSet, kosAVLTreeSet);
    }

}