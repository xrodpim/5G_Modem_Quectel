# 5G_Modem_Quectel

Java software for managing the Quectel RM520N-GL 5G modem and collecting 5G signal metrics.

## Quectel RM520N-GL Board Management Software
### Project for collecting 5G signal fingerprints in real time

## Overview

In a 5G network, RF signals are received by a user equipment (UE), such as a 5G modem or a mobile phone. These signals have measurable characteristics that are useful in projects focused on collecting 5G signal metrics, whether for signal quality analysis, noise studies, or estimating the distance between the RF source and the UE.

One commercially available UE in the telecommunications market is the RM520N-GL board, manufactured by Quectel. It is a 5G modem.

This project provides a software solution for managing the RM520N-GL board, making it possible to control the device and read metrics from the received 5G signal.

This repository contains a concise version of the project documentation.

## Purpose

This project shows how to use the software built to manage the UE (the 5G modem mentioned above) and how to maintain it.

## Intended Audience

This project is intended for:

- Java software developers who want to extend or maintain the codebase
- Anyone interested in controlling the RM520N-GL modem to obtain specific metrics from a 5G signal received in the same cell where the UE is anchored, in real time

## Software Implementation

The software was developed in Java. It sends AT commands to the Quectel RM520N-GL modem in a defined order in order to collect metrics from the current serving 5G cell.

The commands used are AT commands, fully described in Quectel document [1]. Not all commands from document [1] are implemented in this software, but the implemented ones are sufficient for the purpose of reading the selected 5G signal metrics.

Communication with the modem is performed through a USB cable using serial communication. The software uses the computer's USB2 port for this communication. The same USB cable can also power the Quectel board.

The code is organized into the following packages:

```text
main
└── java
    └── com
        └── example
            └── quectel
                ├── file
                ├── metrics
                ├── ueCommands
                └── util
└── test
    └── java
        └── com
            └── example
                └── quectel

---

## Main Class

The main class of this project is `Main`, located in the `quectel` package.

The `Main` class opens the communication port with the modem and sends selected AT commands through it. These commands are organized in a proper sequence, and in general this sequence should not be modified.

## Output Data

The metrics collected during program execution generate data sets that can be explored with machine learning, for example. These files can be viewed in Microsoft Excel and are stored as `.csv` files.

## Package Details

### `file`

This package contains the `CSVFileHandler` class. This class is responsible for creating the CSV file and is used multiple times, once for each CSV file written.

If the format of the data written to the CSV files needs to be changed, this is the class that should be modified.

### `metrics`

This package contains the `Metrics` class. This class is a simple Java bean used to store the attributes that represent the metrics considered in this project.

If a new metric needs to be added, this class should be modified. A list of `Metrics` objects is passed to the `CSVFileHandler` class when the metrics must be written to a CSV file.
```
````md id="p9xk2a"
### `ueCommands`

This package contains several Java classes. Each class represents the implementation of a specific AT command.

If a new command needs to be sent to the Quectel board, a new class should be created in this package.

Below is an example of one of these classes:

```java
package com.exemplo.quectel.ueCommands;

// This command controls whether TA echoes characters received from TE in AT command mode.

public class EchoOffCommand extends ExtendedCommand {
    public EchoOffCommand() {
        super("E0"); // Disables AT command echo
        this.typeOfAnswer = "OK";
    }
}
````

```
```

