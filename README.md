#什么是NFC-War？

原mfoc类Application For Android Phone/BlackBerry Phone项目，正式命名为NFC-War。该项目是为了利用Android NFC手机去进行Miafre Classic的安全检测。并且也是为了更好的延伸类似Proxmark3之类的RFID安全检测设备的功能。

#NFC-War的功能列表：

	•	利用预设置的Key列表进行穷举，从而获取对应的区域数据
	•	经过穷举后获取到的数据可以保存为dump文件到指定目录（预览版不可选择KeyA/KeyB导出）
	•	支持导入Proxmark3的dumpkeys.bin文件（预览版不支持）
	•	支持导入Proxmark3的dumpdata.bin文件（预览版不支持）
	•	无限制加入Key条目
	•	支持写入白卡（预览版不可选择KeyA/KeyB写入、无法修改白卡原KeyA/KeyB）
	•	加入数据对比功能（不支持手机版，Just for Nexus7）

#NFC-War修改日志：

	1.	V0.2版本修改内容：
	•	修复读卡若干BUG
	•	修复History无记录闪退
	•	修复增加Key条目为空BUG
	•	修复FeedBack闪退


#What’s NFC-War?

It’s the previous project which mfoc-like application for Android, and re-named as NFC-War. It could be installed in Android/ BlackBerry Phones with NFC function to detect Miafre Classic vulnerabilities, we call it a swiss knife of Proxmark3.

#NFC-War Specs:

1. Brute-Force with built-in Keys to acquire specific block data
2. Support to save as dump file to specific directory( can’t choose KeyA/ KeyB with Preview)
3. Support to import dumpkeys.bin of Proxmark3 (not available in Preview)
4. Support to import dumpdata.bin of Proxmark3 (not available in Preview)
5. Unlimited Key importation
6. Support to write in BlankCard (can’t choose to Write KeyA/ KeyB, can’t modify original KeyA/ KeyB of BlankCard )
7. Data Diff (Only available in Nexus 7 )

#NFC-War Change Log:

Fix several bugs while reading cards
Fix “Null history record force exit” bug
Fix bug in key number display
Fix “Feedback Force Exit” bug
