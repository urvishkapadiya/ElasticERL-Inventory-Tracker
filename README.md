# ElasticERL-Inventory-Tracker

**Introduction**

Welcome to the Elastic Equipment History Report Listing (ElasticERL) GitHub repository! ElasticERL is an innovative inventory tracking system designed to provide hospitals with essential information about their equipment inventory. This system aims to prevent the loss of valuable equipment and maintain accurate records of Equipment Identification Numbers (EINs). Each EIN is a unique 8-digit code, ensuring precise tracking and management.

ElasticERL is built to adapt to the specific needs of different hospitals, from small city hospitals with a few hundred pieces of equipment to large provincial/state or even country-level hospitals with tens of thousands or millions of pieces of equipment. It offers a flexible and smart Abstract Data Type (ADT) known as ElasticERL to efficiently manage equipment history reports and perform various operations on the inventory data.

This README provides an overview of ElasticERL, its features, design decisions, and instructions on how to use it. We encourage you to explore and contribute to this open-source project to enhance its capabilities.

**Project Description**

EHITS (Elastic Hospital Inventory Tracking System) is a robust inventory tracking solution with the following key features:

Equipment Identification Number (EIN): EHITS uses a unique 8-digit code for each equipment item, ensuring precise tracking. For example, # EIN: 89700035.

Scalable Lists: EHITS supports various types of lists tailored to different hospital sizes. It seamlessly handles equipment lists for small city hospitals, provincial/state hospitals, and country-level hospitals with varying sizes of inventory.

Equipment History: Maintaining a comprehensive equipment history is crucial for tracking hospital inventory. ElasticERL is designed to store historical records in reverse chronological order, allowing users to access the most recent information easily.

Smart and Flexible ADT: ElasticERL introduces the ElasticERL ADT, a powerful tool for Equipment History Report Listing. It offers efficient operations, such as adding and removing entries, retrieving values, and finding predecessors and successors for a given EIN.

**ElasticERL Methods**

The ElasticERL ADT implements the following methods:

SetEINThreshold(Size): Initializes the size of the list, determining the underlying data structure (e.g., Tree, Hash Table, AVL Tree) based on the size.

generate(): Generates a new, non-existing 8-digit key for equipment items.

allKeys(ElasticERL): Returns all keys in ElasticERL as a sorted sequence.

add(ElasticERL, key, value): Adds an entry for the given key and value.

remove(ElasticERL, key): Removes the entry for the given key.

getValues(ElasticERL, key): Returns the values associated with the given key.

nextKey(ElasticERL, key): Returns the key for the successor of the given key.

prevKey(ElasticERL, key): Returns the key for the predecessor of the given key.

rangeKey(key1, key2): Returns the number of keys within the specified range of key1 and key2.

**Performance Considerations**

The performance of ElasticERL adapts to the size of the data:

For small datasets (few hundreds), it optimizes memory usage but may use slightly slower sorting algorithms.

For larger datasets (greater than 1000 or even tens of thousands), it may consume more memory but offers faster sorting algorithms.

The goal is to keep operations on a single ElasticERL entry between O(1) and O(log n) and operations on a complete ElasticERL not exceeding O(n^2).

**Design Decisions**

ElasticERL employs two data structures, Hashtable and AVL Tree, based on the size of the Equipment Identification Number (EIN) dataset:

**Hashtable:** Used for smaller datasets (threshold <= 1000) to optimize memory usage and provide efficient operations.

**AVL Tree:** Employed for larger datasets (threshold > 1000) to maintain balanced trees and ensure fast operations.

**Assumptions and Semantics**

ElasticERL assumes that each node in the tree has a unique integer key and a string value.

The tree maintains the height-balance property at all times, with the height difference between left and right subtrees of any node not exceeding 1.

An optimized hash function is used to minimize collisions in the Hashtable data structure.

**Performance**

ElasticERL offers efficient performance for various sizes of equipment datasets:

Small Datasets (Threshold ≤ 1000): Optimizes memory usage with slightly slower sorting algorithms.

Large Datasets (Threshold > 1000): Provides fast operations with increased memory consumption due to AVL Tree usage.

The choice of data structure (Hashtable or AVL Tree) adapts to the size of your dataset, ensuring that operations remain efficient.

