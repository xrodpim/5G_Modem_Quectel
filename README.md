# 5G_Modem_Quectel

Java software for managing the Quectel RM520N-GL 5G modem and collecting real-time 5G signal metrics.

## Overview

In a 5G network, RF signals are received by user equipment (UE), such as a 5G modem or a mobile phone. These signals have measurable characteristics that are useful in projects focused on collecting 5G signal metrics, whether for signal quality analysis, noise studies, or estimating the distance between the RF source and the UE.

One commercially available UE in the telecommunications market is the RM520N-GL board manufactured by Quectel. It is a 5G modem.

This project provides a software solution for managing the RM520N-GL board, making it possible to control the device and read metrics from the received 5G signal.

This repository contains a concise version of the project documentation.

## Purpose

This project shows how to use the software built to manage the UE (the 5G modem mentioned above) and how to maintain it.

## Intended Audience

This project is intended for:

- Java software developers who want to extend or maintain the codebase
- Anyone interested in controlling the RM520N-GL modem to obtain specific metrics from a 5G signal received in the same cell where the UE is anchored, in real time

## Software Implementation

The software was developed in Java. It sends AT commands to the Quectel RM520N-GL modem in a defined order to collect metrics from the current serving 5G cell.

The commands used are AT commands, fully described in Quectel document [1]. Not all commands from document [1] are implemented in this software, but the implemented ones are sufficient for the purpose of reading the selected 5G signal metrics.

Communication with the modem is performed through a USB cable using serial communication. The software uses the computer's USB2 port for this communication. The same USB cable can also power the Quectel board.

## Project Structure

```text
main
└── java
    └── com
        └── exemplo
            └── quectel
                ├── file
                ├── metrics
                ├── ueCommands
                └── util
└── test
    └── java
        └── com
            └── exemplo
                └── quectel
```

## Main Class

The entry point of the project is the `Main` class, located in the `quectel` package.

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

### `ueCommands`

This package contains several Java classes. Each class represents the implementation of a specific AT command.

If a new command needs to be sent to the Quectel board, a new class should be created in this package.

Each command class extends `ExtendedCommand` and defines the AT command itself in its constructor. The constructor also defines the expected response type.

Some command classes also implement a `handleResponse()` method. This method uses a parser that knows how to interpret data returned by the modem.

Example:

```java
package com.exemplo.quectel.ueCommands;

// This command controls whether TA echoes characters received from TE in AT command mode.

public class EchoOffCommand extends ExtendedCommand {
    public EchoOffCommand() {
        super("E0"); // Disables AT command echo
        this.typeOfAnswer = "OK";
    }
}
```

### `util`

This package contains the `PromptPrincipal` class.

When the `Main` class sends commands to the UE, it does so through `PromptPrincipal`. That class provides the methods for sending AT commands and invokes the corresponding `handleResponse()` methods from the command classes.

## Response Handling

Whenever a command is sent, the board returns a response that usually contains the string `OK`.

Some responses also include additional data at the same time. For that reason, the command base class is designed to verify whether a response was received, while the more complex parsing logic is handled by the specific command classes.

The `checkResponse()` method in `ExtendedCommand` is a simple method that verifies whether the modem returned any response. This check uses a 30-second timeout.

The more complex analysis of UE responses is handled by `handleResponse()`.

Example response:

```text
+QENG: "servingcell","LIMSRV","NR5G-SA","TDD",001,01,000000E00,0,1,641280,78,3,-83,-11,23,1,44
OK
```

This response is obtained, for example, by the command defined in the `CellsInformationQuery` command class.

## Logs

All execution logs are generated with `System.out.println`.

A shell script used to run the program redirects the log into a file named `logE5GUE.txt`. The log contains information such as:

```text
Enviado: AT+QRSRQ
+QRSRQ: -10,-10,-32768,-32768,NR5G
OK
Comando RSRQ Query executado com sucesso.
-------------------------------------------
```

This means:

- the first line shows the command that was sent in its full form
- the following lines show the response that was received, which may include `OK`
- the last line explains whether the command execution succeeded or failed

The log also includes information such as whether the UE is attached to the test network:

```text
Acampado na rede de testes 00101. Vezes: 1
NÃO está acampado na rede de testes 00101. Vezes: 0
```

When the UE is correctly attached to the serving cell and registered in the 5G RAN, the log may show a line similar to:

```text
+QENG: "servingcell","NOCONN","NR5G-SA","TDD",001,01,000000E00,0,1,641280,78,3,-92,-11,21,1,38
```

When the UE is registered in the cell, values such as `RSRP_PRX`, `RSRP_DRX`, `RSRQ_PRX`, `RSRQ_DRX`, `SINR_PRX`, and `SINR_DRX` are expected to be different from zero.

Good-quality signals tend to present the least negative possible `RSRP` and the highest possible `SINR`.

## How to Use the Software

The system depends on a laboratory setup that includes a 5G RF signal source, such as a gNB. It also depends on the correct connection of the 5G modem to a notebook running this software, with communication between modem and notebook established through a USB serial link.

### Required Lab Setup

The lab environment must include:

- USRP B210 and OpenAirInterface software, as described in TCC document [2]
- a notebook with USB 3.0 for the gNB side
- a notebook with USB 2.0 for the UE side
- a Quectel RM520N-GL modem connected as the UE

The TCC document [2] explains how to build a 5G radio source in the laboratory using the USRP B210. It implements the 5G RAN and is managed by OpenAirInterface software.

### Signal Attenuation in the Lab

Depending on the antenna connected to the USRP B210, signal attenuation may need to be adjusted or removed in the laboratory.

With OpenAirInterface, this can be done in configuration files such as:

```text
.../openairinterface5g/targets/PROJECTS/GENERIC-NR-5GC/CONF/gnb.sa.band78.fr1.106PRB.usrpb210.conf
.../openairinterface5g/targets/PROJECTS/GENERIC-NR-5GC/CONF/gnb.sa.band41.fr1.106PRB.usrpb210.conf
```

The first file configures transmission in band 78, and the second one in band 41.

Values such as the following can be set to zero to reduce attenuation as much as possible:

```text
ssPBCH_BlockPower = 0;
att_tx = 0;
max_pdschReferenceSignalPower = 0;
```

### Running the gNB

After installing OpenAirInterface, connect the USRP B210 to the notebook's USB 3.0 port and run the 5G Core and the 5G RAN.

Start the core network:

```bash
cd ./oaisoftware/oai-cn5g-fed/docker-compose/ && sudo python3 core-network.py --type start-basic --scenario 1 && cd ../../../
```

This command creates Docker containers and corresponds to the 5G Core.

Then start the 5G RAN for band 78:

```bash
cd ./oai_ran_5G/openairinterface5g/cmake_targets/ran_build/build/ && sudo ./nr-softmodem -E --sa -O ../../../targets/PROJECTS/GENERIC-NR-5GC/CONF/gnb.sa.band78.fr1.106PRB.usrpb210.conf --continuous-tx --gNBs.[0].min_rxtxtime 6 && cd ../../../../
```

To run another gNB, for example with band 41, use:

```bash
cd ./oai_ran_5G/openairinterface5g/cmake_targets/ran_build/build/ && sudo ./nr-softmodem -E --sa -O ../../../targets/PROJECTS/GENERIC-NR-5GC/CONF/gnb.sa.band41.fr1.106PRB.usrpb210.conf --continuous-tx --gNBs.[0].min_rxtxtime 6 && cd ../../../../
```

To stop the 5G RAN, press `Ctrl + C` in the prompt. To stop the 5G Core, run:

```bash
cd ./oaisoftware/oai-cn5g-fed/docker-compose/ && sudo python3 core-network.py --type stop-basic --scenario 1 && cd ../../../
```

### Running the Quectel RM520N-GL Management Software

Finally, run the command that starts this Java project and begins the 5G signal metrics collection:

```bash
mvn install && sudo usermod -a -G dialout $USER && script -q -c "sudo java -jar target/quectel-at-client-1.0-SNAPSHOT.jar /dev/ttyUSB2" logE5GUE.txt
```

The directory where this command is executed must contain the `src` (source code) and `target` folders. Any change made to the project source code in `src` will be included the next time the command is executed.

Make sure Maven is installed on the machine where this command will run. Picocom must also be installed on the operating system.

The log file generated is a `.txt` file and is defined at the end of the command.

### User Interface

The user interface was designed for the system prompt. There is no graphical interface in this software.

When execution starts, the prompt shows options such as:

```text
Enviado: AT+QNWPREFCFG="mode_pref",NR5G
Comando Set 5G SA executado com sucesso.
---Em que banda você quer ancorar seu UE nesse momento?
(A) - n78
(B) - n41
(C) - Outra
Digite A, B ou C:
```

After choosing one option, for example `A`, the 5G modem anchors to a cell using that band. If no 5G signal is available in the environment for the selected band, the UE will not anchor to any 5G cell.

Then the following prompt appears:

```text
Qual é o MCC e o MNC registrados no SIMCard? Digite abaixo o MCC.
MCC:
```

The MCC and MNC stored on the SIM card must match the MCC and MNC configured in OpenAirInterface for the 5G cell. In OpenAirInterface, the values are usually `MCC = 001` and `MNC = 01`.

Finally, the following option appears:

```text
Iniciando coleta de dados. Digite as coordenadas X,Y onde você está nesse momento:
X:
```

At this moment, the user must declare the current `X`, `Y`, and `Z` coordinates. After entering the coordinates, the software performs dozens or hundreds of metric collections at that point and stores them in a new CSV file.

Example:

```text
>>> Arquivo de métricas gerado com sucesso = Metricas_5G_20251008_114741_071.csv
```

Then the following options are presented:

```text
Escolha a melhor opção abaixo:
(A) - Desejo mover-me para outro ponto (X,Y,Z) e coletar métricas dele também.
(B) - Desejo interromper agora a execução de coletas de métricas 5G. (Exit)
(C) - Desejo coletar métricas com outra banda 5G. (Ancorar em outra célula)
Digite A ou B ou C:
```

The generated metrics file name is displayed, and then it is possible to move the UE to another point in space and repeat the procedure. If the UE does not need to move, it is possible to stop or switch bands.

## Maintenance

Study the explanations above, modify the code as needed, and run the `rodaCampanhaDeColeta.sh` script included in this project.

## Tests

Functional tests were performed in the laboratory.

A gNB built with a USRP B210 and OpenAirInterface was used together with a notebook running Ubuntu 20.04 LTS and a USB 3.0 port. A 5G modem was also used as the UE, connected to a notebook with USB 2.0 and a newer Ubuntu version. The gNB used band 78.

## Configuring the Data Collection Campaign

In the `Main` class, you can find the following variables:

```java
int loopIterations = 50; // Number of times the 5G signal metrics should be read at the same fixed point in space.
int delayBetweenIterations = 300; // Waiting time between readings, in milliseconds.
```

Adjust these values as needed to change the number of metric readings performed at each `X`, `Y`, and `Z` point over time.

## Suggested Next Steps

- build additional gNBs configured with different bands, such as band 77 and band 41
- create a new UE software based on this one, so the UE can move freely in the lab and collect metrics from all bands periodically, without collecting `X`, `Y`, and `Z` this time, and send that information to a database or another system for later processing
- provide suitable antennas for the USRP B210 boards and a controlled power supply for each board; this will likely improve 5G signal quality and may allow the UE to remain registered in the cells for longer periods

## References

[1] Quectel. *RG520N & RG525F & RG5x0F & RM5x0N & RM521F Series AT Commands Manual*.

[2] Carlos Antonio de Lima Filho. *Montando uma Rede 5G Virtualizada com o Open Air Interface e Plataformas de Rádio Definido por Software*. Federal University of Rio Grande do Norte, 2023.

[3] Quectel. *RM520N-GL Hardware Design*, 2022.

[4] Quectel discussion forum: https://forums.quectel.com/

---

Author: Rodrigo Pimenta Carvalho  
Last updated: 2025-10-10
