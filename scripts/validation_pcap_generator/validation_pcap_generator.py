import base64 as b64

from scapy.all import *
from scapy.layers.inet import IP, TCP

from random_arab_lines import RANDOM_ARAB_LINES
from validation_pcap_generator_config import *

def build_packet(
        source: tuple[str, int],
        destination: tuple[str, int],
        tcp_flags: str,
        seq: int = 0,
        ack: int = 0,
        payload: str = '') -> Packet:
    return IP(src=source[0], dst=destination[0]) / TCP(sport=source[1], dport=destination[1], flags=tcp_flags, seq=seq, ack=ack) / Raw(load=payload)


def generate_random_message():
    return random.choice(RANDOM_ARAB_LINES)


def generate_communication(source: tuple[str, int], destination: tuple[str, int], comm_length: int) -> list[Packet]:

    packets = []

    seq = random.randint(0, 0xFFFF)
    ack = random.randint(0, 0xFFFF)

    # TODO Triple handshake

    for i in range(comm_length):
        # TODO add timestamp changes
        payload: str = generate_random_message()
        packet1 = build_packet(source, destination, tcp_flags='PA', seq=seq, ack=ack, payload=payload)

        packets.append(packet1)
        seq = (seq + len(payload)) % 0xFFFF

        payload = generate_random_message()
        packet2 = build_packet(destination, source, tcp_flags='PA', seq=ack, ack=seq, payload=payload)
        packets.append(packet2)
        ack = (ack + len(payload)) % 0xFFFF

    # TODO Finish comm

    return packets

def generate_real_convo(source: tuple[str, int], destination: tuple[str, int], conversation: list[str]) -> list[Packet]:
    packets = []
    seq1 = random.randint(0, 0xFFFF)
    seq2 = random.randint(0, 0xFFFF)

    sentence: str
    sentence_index: int
    for sentence_index, sentence in enumerate(conversation):
        src: tuple[str, int]
        dst: tuple[str, int]
        seq: int
        ack: int
        if sentence_index % 2 == 0:
            src = source
            dst = destination
            seq = seq1
            ack = seq2
            seq1 = (seq1 + len(sentence)) % 0xFFFF
        else:
            src = destination
            dst = source
            seq = seq2
            ack = seq1
            seq2 = (seq2 + len(sentence)) % 0xFFFF

        packets.append(build_packet(src, dst, tcp_flags='PA', seq=seq, ack=ack, payload=sentence))

    return packets


def jumble_packets(haystack: list[Packet], packets: list[Packet]) -> list[Packet]:

    jumbled_packets = [haystack.pop(0)]

    for i in range(len(haystack) + len(packets)):
        if random.randint(0, 4) == 0 or len(packets) == 0 and len(haystack.pop(0)) != 0:
            jumbled_packets.append(haystack.pop(0))
        else:
            jumbled_packets.append(packets.pop(0))

    return jumbled_packets


def encrypt(data: str, key: str) -> bytes:
    input_bytes = data.encode('utf-8')
    key_bytes = key.encode('utf-8')
    return b64.b64encode(bytes([b ^ key_bytes[i % len(key_bytes)] for i, b in enumerate(input_bytes)]))


def generate_file():
    packets = generate_communication((VERY_EVIL_DOMAIN, VERY_EVIL_PORT), (LESS_EVIL_DOMAIN, LESS_EVIL_PORT), 27)

    key = "mistake"

    enc_convo = []
    for sentence in CONVERSATION:
        cipher = encrypt(sentence, key)
        enc_convo.append(cipher)

    real_conversation = generate_real_convo((NOOR_DOMAIN, NOOR_PORT), (MUHAMMAD_DOMAIN, MUHAMMAD_PORT), enc_convo)

    jumbled_packets = jumble_packets(packets, real_conversation)

    wrpcap('very_secret_way_to_do_things.pcap', jumbled_packets)

if __name__ == '__main__':
    generate_file()