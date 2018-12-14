Author: Bryce Di Geronimo

Code based off of pseudocode from CLRS

CIS 313, Intermediate Data Structures
Fall 2018

CIS 313 Lab 4

This lab involves implementing a Red-Black Tree

Overview
You will construct a Red-Black Tree of numbers.  You will extend your code in programming assignment 2 to now include balancing operations.

A BST is a Red-Black tree if it satisfies the following Red-Black Properties:
	1.  Every node is either red or black
	2.  Every leaf node counts as black
	3.  If a node is red, then both its children are black
	4.  Every simple path from a node to a descendant leaf contains the same number of black nodes
	5.  The root node is always black

The program should continually accept instructions until exit is entered.

In particular, you will implement the following functionality for your Red-Black Tree:

insert
Perform BST insert
Check to see if the tree breaks any of the Red-Black Properties
If so, perform the appropriate rotations and or re-colorings

delete
Perform BST delete
Check to see if the tree breaks any of the Red-Black Properties
If so, perform the appropriate rotations and or re-colorings
Note:  You will not be asked to remove the most difficult nodes (ie) you won’t need to recursively rebalance

search
Return the node corresponding to the given number
Print ”Found” if the corresponding key is in the tree.
Otherwise, print ”Not Found”

leftRotate
If x is the root of the tree to rotate with left child subtree T1 and right child y, where T2 and T3 are the left and right children of y:
	•x becomes left child of y and T3 as its right child of y
	•T1 becomes left child of x and T2 becomes right child of x

rightRotate
If y is the root of the tree to rotate with right child subtree T3 and left child x, where T1 and T2 are the left and right children of x:
	•y becomes right child of x and T1 as its left child of x
	•T2 becomes left child of y and T3 becomes right child of y

Additionally, implement the following instructions in the main() function in lab4.java

traverse
Perform the preorder traversal of the Red-Black Tree

exit
Exit the program
Print ”Successful Exit”

Input Description
The input will be a text file, for example inSample.txt below will be provided. Each of the lines(unknown how many) will contain a different set of words specifying a task along with a number(if applicable). You should create an empty Red-Black Tree (with root null), and then perform a sequence of actions on that tree.

Note: You should implement your Red-Black tree with generics, but create a Red-Black tree that takes in integers.


insert 10
insert 5
insert 20
insert 3
traverse
insert 2
traverse
search 17
insert 1
delete 20
traverse
exit

Output Description

Using the sample input above, your program should output:
10 5 3 20
10 3 2 5 20
Not Found
3 2 1 10 5
Successful Exit


