# Lab7

## Overview
In lab7, you complete an `AVLTree` class that stores `int` keys. You will write seven methods:
- Two methods deal with AVL tree heights. 
- Two methods perform rotations
- A method that rebalances a node.
 - The *insert* method.
 - The *contains* method.

## Objectives
- Storing the height of every subtree in its top node, and updating it in constant time.
- Detecting an unbalanced node from its balance factor, and fixing it with a single or a double rotation.
- Inserting into a binary search tree recursively while rebalancing every node on the way back up.
- Searching a binary search tree without changing it.

## Files
| File | What to do |
|:--|:--|
| `README.md` | These instructions. |
| `AVLTree.java` | Complete the seven methods marked `TODO` |
| `Main.java` | Example tests. Run it to check your work. Do NOT modify it |

Each of the seven methods currently throws an `UnsupportedOperationException`. Replace that line with your code. The files compile as they are, so you can work on one method at a time.

### Level-order notation
Lab7's instructions write a tree as a list such as `[50, 30, 70, 20, 40]`. Read it level by level, from left to right. The first key is the root. After it come the children of each node in turn, left child first, with `null` for a missing child. The children of a missing node are not listed, and `null`s at the end are left out. `[]` is the empty tree.

Also, when heights matter, each key is followed by a colon and its stored height. For example, `[30:2, 20:1, null, 10:0]` and `[20:1, 10:0, 30:0]` are these two trees:

```text
      30                20
     /                 /  \
   20                10    30
  /
10
```

The one on the left is not balanced: the balance factor of 30 is +2. Two provided methods use this notation:
- `AVLTree.fromLevelOrder(30, 20, null, 10)` builds a tree and gives every node its correct height. The tree does not have to be balanced, so you can also build the trees that *rebalance* method has to fix.
- `toString()` writes a tree with its heights, and `AVLTree.toString(node)` writes the subtree whose top node is `node`. 

## Rules
1. **Keep the provided code.** Do not change anything in the `PROVIDED CODE` section of `AVLTree.java`: the `Node` class, the `root` field, the constructor, `height`, `insert(int key)`, `fromLevelOrder` and the two `toString` methods. Do not rename the class or the seven methods, change their parameters or return types, or replace the `package` line. 
2. **Do not add fields.** Do not add instance variables or static variables. Use local variables and private helper methods instead; you may add as many private helper methods as you like. 
3. **Exceptions.** The only exception your code may throw is an `IllegalArgumentException` from `insert` when the key is already in the tree. Do not call `System.exit`.

## Rubric
Lab7 has 7 methods that you need to implement. And there are five test cases for each method. So your submission must pass 35 test cases in total.

| Method | Marks per test case | Marks |
|:--|:--:|:--:|
| `updateHeight` | 2 | 10 |
| `balanceFactor` | 2 | 10 |
| `rotateRight` | 3 | 15 |
| `rotateLeft` | 3 | 15 |
| `rebalance` | 4 | 20 |
| `insert` | 4 | 20 |
| `contains` | 2 | 10 |
| **Total** | | **100** |

- **Compiling.** If `AVLTree.java` does not compile, the whole assignment scores **0**.
- **Unexpected exceptions.** If a method throws an exception that the instructions did not allow, in **any** of its test cases, that method scores **0** for **all five** of its test cases. This includes a `NullPointerException`, a `StackOverflowError`, the `UnsupportedOperationException` of a method you have not implemented yet.
- **Time limit.** A test case that runs for more than 5 seconds (for example, because of an infinite loop) scores 0.
- **Methods build on each other.** The rotations call `updateHeight`; `rebalance` calls `updateHeight`, `balanceFactor` and the rotations; `insert` calls `rebalance`. A bug in one method can therefore cost marks in the methods that use it too, so test each method before you go on to the next. 














