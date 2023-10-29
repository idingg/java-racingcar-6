package racingcar;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.Test;

import static camp.nextstep.edu.missionutils.test.Assertions.assertRandomNumberInRangeTest;
import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class ApplicationTest_function_list extends NsTest {

    void testPrivateMethod(Class<?> testClass, String testMethodName, List<List<Object>> testCase) {

        for (List<Object> input : testCase) {
            // private method reflection 사용
            Object returnValue = new Object();
            try {
                // reflection
                Class<?> parameterClasses[] = Arrays.asList(testClass.getDeclaredMethods())
                        .stream()
                        .filter(x -> x.getName().equals(testMethodName))
                        .toList()
                        .get(0)
                        .getParameterTypes();
                Method testMethod = testClass.getDeclaredMethod(testMethodName, parameterClasses);
                testMethod.setAccessible(true);

                Object[] parameter = input.subList(0, input.size() - 1).toArray();// 메소드 입력값
                returnValue = testMethod.invoke(testClass, parameter);// 실행
            } catch (Exception e) { // 메소드명 오류시 예외처리
                assertThatExceptionOfType(IllegalArgumentException.class);
                continue;
            }
            Object expectResult = input.get(input.size() - 1);
            assertThat(returnValue).isEqualTo(expectResult);
        }
    }

    @Test
    void 기능목록_테스트_시작_문자열_출력() {
        Print.startString();
        assertThat(output()).isEqualTo("경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)");
    }

    @Test
    void 기능목록_테스트_자동차_이름_입력() {
        List<String> testCase = Arrays.asList(
                "pobi,woni,jun,0,1,2,3,4,5,6,7,8,9",
                "pobi,0,1,2,3,4,5,6,7,8,9,woni,jun",
                "0,1,2,3,4,5,6,7,8,9,pobi,woni,jun");

        for (int i = 0; i < testCase.size(); i++) {
            final byte[] buf = String.join("\n", testCase).getBytes();
            System.setIn(new ByteArrayInputStream(buf));
            assertThat(Input.inputCarName())
                    .containsAll(Arrays.asList("pobi", "woni", "jun", "0", "5", "9"));
        }
    }

    @Test
    void 기능목록_테스트_입력_문자열_분할() {
        Class<?> testClass = Input.class;
        String testMethodName = "rawToList";
        List<List<Object>> testCase = Arrays.asList(
                Arrays.asList("a,bb,ccc", Arrays.asList("a", "bb", "ccc")),
                Arrays.asList("pobi", Arrays.asList("pobi")));
        testPrivateMethod(testClass, testMethodName, testCase);
    }

    @Test
    void 기능목록_테스트_자동차_이름_입력_오류_처리() {
        // void checkCarNameError(List<String> carNames) throws IllegalArgumentException
        Class<?> testClass = Input.class;
        String testMethodName = "checkCarNameError";
        List<List<Object>> testCase = Arrays.asList(
                Arrays.asList(Arrays.asList("pobi", "woni", "jun"), null),
                Arrays.asList(Arrays.asList("1"), null),
                Arrays.asList(Arrays.asList("pobi", "woni", "123456"), new IllegalArgumentException()));
        testPrivateMethod(testClass, testMethodName, testCase);
    }

    @Test
    void 기능목록_테스트_빈_문자열_검사() {
        Class<?> testClass = Input.class;
        String testMethodName = "isBlank";
        List<List<Object>> testCase = Arrays.asList(
                Arrays.asList("pobi", false),
                Arrays.asList("", true));
        testPrivateMethod(testClass, testMethodName, testCase);
    }

    @Test
    void 기능목록_테스트_1_5자_검사() {
        Class<?> testClass = Input.class;
        String testMethodName = "isLengthOutOf1To";
        List<List<Object>> testCase = Arrays.asList(
                Arrays.asList("pobi", 5, false),
                Arrays.asList("", 5, true),
                Arrays.asList("123456", 5, true),
                Arrays.asList("1234", 3, true));
        testPrivateMethod(testClass, testMethodName, testCase);
    }

    @Test
    void 기능목록_테스트_자동차_이름_저장() {

    }

    @Test
    void 기능목록_테스트_경주() {

    }

    @Test
    void 기능목록_테스트_시도_횟수_입력() {
        List<String> testCaseGood = Arrays.asList(
                "1",
                "1234567890",
                "9223372036854775807");

        List<String> testCaseException = Arrays.asList(
                "",
                "0",
                "-1",
                "-9223372036854775809",
                "9223372036854775808");
        List<String> testCase = new ArrayList<>(testCaseGood);// + testCaseException;
        testCase.addAll(testCaseException);

        final byte[] bufTestCaseException = String.join("\n", testCase).getBytes();
        System.setIn(new ByteArrayInputStream(bufTestCaseException));
        for (int i = 0; i < testCaseGood.size(); i++) {
            String actual = Input.inputRepetitions().toString();
            assertThat(actual).isEqualTo(testCaseGood.get(i));
        }

        for (int i = 0; i < testCaseException.size(); i++) {
            assertThrows(IllegalArgumentException.class, () -> {
                Input.inputRepetitions();
            });
        }
    }

    @Test
    void 기능목록_테스트_시도_횟수_질문_문자열_출력() {

    }

    @Test
    void 기능목록_테스트_시도_횟수_입력_오류_처리() {

    }

    @Test
    void 기능목록_테스트_숫자_자연수() {

    }

    @Test
    void 기능목록_테스트_시도_횟수만큼_전체_순환() {

    }

    @Test
    void 기능목록_테스트_전체_자동차_순환() {

    }

    @Test
    void 기능목록_테스트_한_자동차_진행() {

    }

    @Test
    void 기능목록_테스트_0_9_무작위_값_생성() {

    }

    @Test
    void 기능목록_테스트_4이상_인지_검사() {

    }

    @Test
    void 기능목록_테스트_값에_따라_자동차_전진_정지() {

    }

    @Test
    void 기능목록_테스트_현재_상태_출력() {

    }

    @Test
    void 기능목록_테스트_경기_상황_출력() {

    }

    @Test
    void 기능목록_테스트_우승자_목록을_형식에_맞춰_출력() {

    }

    @Test
    void 기능목록_테스트_우승자_계산() {

    }

    @Override
    public void runMain() {
        Application.main(new String[] {});
    }
}
