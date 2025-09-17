import { Button, Image, ScrollView, Text, TextInput, View } from "react-native";

export default function HomePage() {
  return (
    <View
      style={{
        flex: 1,
        justifyContent: "center",
        alignItems: "center",
        alignContent: "center",
      }}
    >
      <ScrollView>
        <Text>函数式组件</Text>
        <Image
          source={{ uri: "https://reactnative.dev/img/tiny_logo.png" }}
          style={{ width: 200, height: 200 }}
        />
        <TextInput
          placeholder="输入文字..."
          style={{ height: 40, borderColor: "gray", borderWidth: 1 }}
        />
        <Button title="按钮" />
      </ScrollView>
    </View>
  );
}
